package com.example.pricechecker.configurations;

import com.example.pricechecker.logic.httpRequests.HttpRequestsExecutor;
import com.example.pricechecker.logic.parsing.classParsers.ClassParser;
import com.example.pricechecker.logic.parsing.classParsers.ProductParser;
import com.example.pricechecker.logic.parsing.urlParsers.UrlParser;
import com.example.pricechecker.logic.productOperations.ProductSorter;

public class ProjectConfiguration {
    private final ProductConfiguration productConfiguration = new ProductConfiguration();
    private final ProductSorter sorter = new ProductSorter();
    private final HttpRequestsExecutor executor = new HttpRequestsExecutor();
    private final ProductParser productParser = new ProductParser(new ClassParser(executor),new UrlParser(executor));
    private final int productsPerSiteCount = 10;
    private final int displayedProductsCount = 5;

    public ProductConfiguration getProductConfiguration() {
        return productConfiguration;
    }

    public ProductSorter getSorter() {
        return sorter;
    }

    public ProductParser getProductParser() {
        return productParser;
    }

    public int getProductsPerSiteCount() {
        return productsPerSiteCount;
    }

    public int getDisplayedProductsCount() {
        return displayedProductsCount;
    }
}
