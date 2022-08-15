package com.example.pricechecker;

import com.example.pricechecker.logic.httpRequests.HttpRequestsExecutor;
import com.example.pricechecker.logic.parsing.classParsers.ClassParser;
import com.example.pricechecker.logic.parsing.urlParsers.UrlParser;
import com.example.pricechecker.configurations.ProductConfiguration;
import com.example.pricechecker.model.Product;
import com.example.pricechecker.logic.productOperations.ProductSorter;
import com.example.pricechecker.logic.parsing.classParsers.ProductParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.stream.Collectors;

public class HelloController {
    @FXML
    private TextField productName;

    @FXML
    private TextArea text;


    @FXML
    void onClick(ActionEvent event) {
        int perSite = 3;
        ProductConfiguration configuration = new ProductConfiguration();
        HttpRequestsExecutor executor = new HttpRequestsExecutor();
        ProductParser productParser = new ProductParser(new ClassParser(executor),new UrlParser(executor));
        productParser.parseProductsAsync(productName.getText(),perSite,configuration.getSiteInfoList(),this::onFound);
    }

    private void onFound(List<Product> products){
        int limit = 4;
        ProductSorter operations = new ProductSorter();
        products = operations.findCheapest(products,limit);
        String str = products.stream().map(Product::toString).collect(Collectors.joining());
        text.setText(str);
    }
}