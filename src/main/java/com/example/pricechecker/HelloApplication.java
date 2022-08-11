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
import org.jsoup.nodes.Element;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
            ElementsSelectionInfo priceInfo = new ElementsSelectionInfo(new URI("https://www.kufar.by/item/164951615"),"div span.styles_main__PU1v4");
            ElementsSelectionInfo nameInfo = new ElementsSelectionInfo(new URI("https://www.kufar.by/item/164951615"),"div h1.styles_brief_wrapper__title__x59rm[data-name='av_title']");
            FieldFromElementsSetter<Product,Long> priceSetter = new FieldFromElementsSetter<>(price,(elements)->{
                String text = elements.first().text();
                return Long.parseLong(text.split(" ")[0]);
            });
            FieldFromElementsSetter<Product,String> nameSetter = new FieldFromElementsSetter<>(name,(elements)->{
               return elements.first().text();
            });
            FieldParseInfo<Product,Long> priceParseInfo = new FieldParseInfo<>(priceSetter,priceInfo);
            FieldParseInfo<Product,String> nameParseInfo = new FieldParseInfo<>(nameSetter,nameInfo);
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