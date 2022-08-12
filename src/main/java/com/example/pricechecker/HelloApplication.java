package com.example.pricechecker;

import com.example.pricechecker.logic.httpRequests.HttpRequestsExecutor;
import com.example.pricechecker.logic.parsing.html.*;
import com.example.pricechecker.model.ClassField;
import com.example.pricechecker.model.FieldSetter;
import com.example.pricechecker.model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        FieldSetter fieldSetter = new FieldSetter();
        try {
            ClassField<Product, Long> price = new ClassField<>(fieldSetter, "price", Product.class, Long.class);
            ClassField<Product,String> name = new ClassField<>(fieldSetter,"name",Product.class,String.class);
            PageParseInfo<Long> priceInfo = new PageParseInfo<>(new URI("https://www.kufar.by/item/164951615"),(html)->{
                Document doc = Jsoup.parse(html);
                String text = doc.select("div span.styles_main__PU1v4").first().text();
                return Optional.of(Long.parseLong(text.split(" ")[0]));
            });
            PageParseInfo<String> nameInfo = new PageParseInfo<>(new URI("https://www.kufar.by/item/164951615"),(html)->{
                Document doc = Jsoup.parse(html);
                String text = doc.select("div h1.styles_brief_wrapper__title__x59rm[data-name='av_title']").first().text();
                return Optional.of(text);
            });
            FieldParseInfo<Product,Long,Long> priceParseInfo = new FieldParseInfo<>(price,(collection)->collection.get(0),List.of(priceInfo));
            FieldParseInfo<Product,String,String> nameParseInfo = new FieldParseInfo<>(name,(collection)->collection.get(0),List.of(nameInfo));
            ClassParseInfo<Product> classParseInfo = new ClassParseInfo<Product>(Product::new,List.of(priceParseInfo,nameParseInfo),new ArrayList<>());
            ClassParser parser = new ClassParser(new HtmlParser(),new HttpRequestsExecutor());
            Product product = parser.parse(classParseInfo);
            System.out.println(product.getPrice());
            System.out.println(product.getName());
        } catch (Exception e){}
    }

    public static void main(String[] args) {
        launch();

    }
}