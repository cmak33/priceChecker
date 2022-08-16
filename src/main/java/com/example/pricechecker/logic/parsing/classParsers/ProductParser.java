package com.example.pricechecker.logic.parsing.classParsers;

import com.example.pricechecker.logic.callbacks.Callback;
import com.example.pricechecker.logic.callbacks.NoArgumentsCallback;
import com.example.pricechecker.logic.callbacks.TimesCalledCallback;
import com.example.pricechecker.logic.builders.ProductInfoBuilder;
import com.example.pricechecker.logic.parsing.urlParsers.UrlParser;
import com.example.pricechecker.logic.parsing.urlParsers.uriFromStringCreator.UriFromStringCreator;
import com.example.pricechecker.logic.parsing.urlParsers.urlCreators.UrlCreatorByNameAndPageNumber;
import com.example.pricechecker.logic.parsing.urlParsers.urlCreators.UrlCreatorByPageNumber;
import com.example.pricechecker.model.Product;
import com.example.pricechecker.model.parseInfo.classInfo.ClassParseInfo;
import com.example.pricechecker.model.parseInfo.siteInfo.SiteInfo;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public record ProductParser(ClassParser classParser,
                            UrlParser urlParser) {

    public List<Product> parseProducts(String productName, int maxPerSite, List<SiteInfo<Product>> siteInfoList) {
        List<Product> products = new ArrayList<>();
        siteInfoList.forEach(siteInfo -> {
            UrlCreatorByNameAndPageNumber urlCreator = new UrlCreatorByNameAndPageNumber(siteInfo.getFindPageFormat(), productName);
            List<Product> siteProducts = parseSiteProducts(urlCreator, maxPerSite, siteInfo);
            products.addAll(siteProducts);
        });
        return products;
    }

    private List<Product> parseSiteProducts(UrlCreatorByPageNumber urlCreator, int maxProductsCountPerSite, SiteInfo<Product> siteInfo) {
        List<String> urls = urlParser.findItemsUrls(urlCreator, maxProductsCountPerSite, siteInfo.getUrlParser());
        List<Product> products = new ArrayList<>();
        urls.forEach(url -> {
            Optional<Product> product = parseProduct(url, siteInfo);
            product.ifPresent(products::add);
        });
        return products;
    }

    private Optional<Product> parseProduct(String url, SiteInfo<Product> siteInfo) {
        Optional<URI> uri = UriFromStringCreator.createUri(url);
        Optional<Product> product;
        if (uri.isPresent()) {
            ClassParseInfo<Product> classParseInfo = createClassParseInfo(uri.get(), siteInfo.getClassParseInfo());
            Product foundProduct = classParser.parse(classParseInfo);
            foundProduct.setSiteName(siteInfo.getSiteName());
            foundProduct.setUrl(url);
            product = Optional.of(foundProduct);
        } else {
            product = Optional.empty();
        }
        return product;
    }

    public CompletableFuture<List<Product>> parseProductsAsync(String productName, int maxProductsCountPerSite, List<SiteInfo<Product>> siteInfoList) {
        CompletableFuture<List<Product>> result = new CompletableFuture<>();
        List<Product> products = Collections.synchronizedList(new ArrayList<>());
        NoArgumentsCallback setCompleteResultCallback = () -> result.complete(products);
        TimesCalledCallback timesCalledCallback = new TimesCalledCallback(setCompleteResultCallback, siteInfoList.size());
        Callback<List<Product>> siteCallback = (list) -> {
            products.addAll(list);
            timesCalledCallback.call();
        };
        siteInfoList.forEach(siteInfo -> {
            UrlCreatorByNameAndPageNumber urlCreatorByNameAndPageNumber = new UrlCreatorByNameAndPageNumber(siteInfo.getFindPageFormat(), productName);
            CompletableFuture.runAsync(()->parseSiteProductsAsync(urlCreatorByNameAndPageNumber, maxProductsCountPerSite, siteInfo).thenAccept(siteCallback::call));
        });
        return result;
    }

    private CompletableFuture<List<Product>> parseSiteProductsAsync(UrlCreatorByPageNumber urlCreator, int maxProductsCount, SiteInfo<Product> siteInfo) {
        CompletableFuture<List<Product>> result = new CompletableFuture<>();
        List<String> urls = urlParser.findItemsUrls(urlCreator, maxProductsCount, siteInfo.getUrlParser());
        List<Product> products = Collections.synchronizedList(new ArrayList<>());
        NoArgumentsCallback resultCompleteCallback = () -> result.complete(products);
        TimesCalledCallback productsCallback = new TimesCalledCallback(resultCompleteCallback, urls.size());
        Callback<Optional<Product>> foundProductCallback = (product) -> {
            product.ifPresent(products::add);
            productsCallback.call();
        };
        urls.forEach(url -> parseProductAsync(url, siteInfo).thenAccept(foundProductCallback::call));
        return result;
    }

    private CompletableFuture<Optional<Product>> parseProductAsync(String url, SiteInfo<Product> siteInfo) {
        CompletableFuture<Optional<Product>> result = new CompletableFuture<>();
        Optional<URI> uri = UriFromStringCreator.createUri(url);
        if (uri.isPresent()) {
            Callback<Product> callback = (product) -> {
                product.setSiteName(siteInfo.getSiteName());
                product.setUrl(url);
                result.complete(Optional.of(product));
            };
            ClassParseInfo<Product> classParseInfo = createClassParseInfo(uri.get(), siteInfo.getClassParseInfo());
            classParser.parseAsync(classParseInfo).thenAccept(callback::call);
        } else {
            result.complete(Optional.empty());
        }
        return result;
    }

    private ClassParseInfo<Product> createClassParseInfo(URI uri, ClassParseInfo<Product> originalInfo) {
        return ProductInfoBuilder.cloneFrom(originalInfo).setURI(uri).build();
    }
}
