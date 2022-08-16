package com.example.pricechecker;

import com.example.pricechecker.configurations.ProjectConfiguration;
import com.example.pricechecker.model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.stream.Collectors;

public class HelloController {
    private final ProjectConfiguration projectConfiguration = new ProjectConfiguration();
    private boolean isSearching = false;

    @FXML
    private TextField productName;

    @FXML
    private TextArea text;


    @FXML
    void onClick(ActionEvent event) {
        if(!isSearching) {
            isSearching = true;
            projectConfiguration.getProductParser().parseProductsAsync(productName.getText(), projectConfiguration.getProductsPerSiteCount(), projectConfiguration.getProductConfiguration().getSiteInfoList())
                    .thenAccept(this::onFound);
        } else{
            text.setText("Wait a little, application is searching now!");
        }
    }

    private void onFound(List<Product> products){
        products = projectConfiguration.getSorter().findCheapest(products,projectConfiguration.getDisplayedProductsCount());
        String productsDisplayString = products.stream().map(Product::toString).collect(Collectors.joining());
        text.setText(productsDisplayString);
        isSearching = false;
    }
}