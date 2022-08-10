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
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.nio.charset.StandardCharsets;
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
            PropertyParseInfo priceInfo = new PropertyParseInfo(new URI("https://www.kufar.by/item/164951615"),"div span.styles_main__PU1v4");
            PropertyParseInfo nameInfo = new PropertyParseInfo(new URI("https://www.kufar.by/item/164951615"),"div h1.styles_brief_wrapper__title__x59rm[data-name='av_title']");
            SimplePropertyParser<Product,Long> parserPrice = new SimplePropertyParser<Product,Long>(price,(elements)->{
                Element el = elements.first();
                return Long.parseLong(el.text().split(" ")[0]);
            });
            SimplePropertyParser<Product,String> parserName = new SimplePropertyParser<>(name,(elements)->{
                return elements.first().text();
            });
            ClassParser<Product> classParser = new ClassParser<Product>(List.of(parserPrice,parserName),new ArrayList<>());
            ClassParserInfo<Product> parserInfo = new ClassParserInfo<>(classParser,new HashMap<>(){
                {put(price,priceInfo);put(name,nameInfo);}
            });
            ParserManager manager = new ParserManager(new HtmlParser(),new HttpRequestsExecutor());
            Product product = new Product();
            manager.parse(parserInfo,product);
            System.out.println(product.getPrice());
            System.out.println(product.getName());
        } catch (Exception e){}
    }

    public static void main(String[] args) {
        launch();

    }
}