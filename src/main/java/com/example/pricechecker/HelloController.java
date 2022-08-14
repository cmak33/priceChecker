package com.example.pricechecker;

import com.example.pricechecker.logic.httpRequests.HttpRequestsExecutor;
import com.example.pricechecker.logic.parsing.classParsers.ClassParser;
import com.example.pricechecker.logic.parsing.html.HtmlParser;
import com.example.pricechecker.logic.parsing.pageParsers.UrlParser;
import com.example.pricechecker.model.Configuration;
import com.example.pricechecker.model.Product;
import com.example.pricechecker.model.ProductOperations;
import com.example.pricechecker.model.ProductParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HelloController {
    @FXML
    private TextField productName;

    @FXML
    private TextArea text;


    @FXML
    void onClick(ActionEvent event) {
        int perSite = 5;
        int limit = 4;
        Configuration configuration = new Configuration();
        HttpRequestsExecutor executor = new HttpRequestsExecutor();
        ProductParser productParser = new ProductParser(new ClassParser(new HtmlParser(),executor),new UrlParser(executor));
        List<Product> list = productParser.parseProducts(productName.getText(),perSite,configuration.getSiteInfoList());
        ProductOperations operations = new ProductOperations();
        list = operations.findCheapest(list,limit);
        String str = list.stream().map(operations::toString).collect(Collectors.joining());
        text.setText(str);
    }
}