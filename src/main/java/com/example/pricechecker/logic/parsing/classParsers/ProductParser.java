package com.example.pricechecker.logic.parsing.classParsers;

import com.example.pricechecker.logic.callbacks.Callback;
import com.example.pricechecker.logic.parsing.classParsers.ClassParser;
import com.example.pricechecker.logic.callbacks.NoArgumentsCallback;
import com.example.pricechecker.logic.callbacks.TimesCalledCallback;
import com.example.pricechecker.logic.builders.ProductInfoBuilder;
import com.example.pricechecker.logic.parsing.urlParsers.UrlParser;
import com.example.pricechecker.model.Product;
import com.example.pricechecker.model.parseInfo.siteInfo.SiteInfo;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ProductParser {
    private final ClassParser classParser;
    private final UrlParser urlParser;


    public ProductParser(ClassParser classParser,UrlParser urlParser) {
        this.classParser = classParser;
        this.urlParser = urlParser;
    }

    public List<Product> parseProducts(String productName, int maxPerSite, List<SiteInfo<Product>> siteInfoList){
        List<Product> products = new ArrayList<>();
        siteInfoList.forEach(siteInfo->{
            List<String> urls = urlParser.findItemsUrls(productName, siteInfo.getFindPageFormat(), maxPerSite, siteInfo.getUrlParser()).stream().limit(maxPerSite).toList();
            urls.forEach(url->{
                try{
                    URI uri = new URI(url);
                    Product product = classParser.parse(ProductInfoBuilder.fromClone(siteInfo.getClassParseInfo()).setURI(uri).build());
                    product.setSiteName(siteInfo.getSiteName());
                    product.setUrl(url);
                    products.add(product);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            });
        });
        return products;
    }

    public void parseProductsAsync(String productName, int maxPerSite, List<SiteInfo<Product>> siteInfoList, Callback<List<Product>> callback){
        List<Product> products = Collections.synchronizedList(new ArrayList<>());
        NoArgumentsCallback returnCallback = ()->callback.call(products);
        TimesCalledCallback timesCalledCallback = new TimesCalledCallback(returnCallback,siteInfoList.size());
        Callback<List<Product>> siteCallback = (list)->{
            products.addAll(list);
            timesCalledCallback.call();
        };
        siteInfoList.forEach(siteInfo->{
            CompletableFuture.runAsync(()->parseSiteProductsAsync(productName,maxPerSite,siteInfo,siteCallback));
        });
    }

    private void parseSiteProductsAsync(String productName, int maxPerSite,SiteInfo<Product> siteInfo,Callback<List<Product>> callback){
        List<String> urls = urlParser.findItemsUrls(productName, siteInfo.getFindPageFormat(), maxPerSite, siteInfo.getUrlParser()).stream().limit(maxPerSite).toList();
        List<Product> products = Collections.synchronizedList(new ArrayList<>());
        NoArgumentsCallback returnCallback = ()-> callback.call(products);
        TimesCalledCallback productsCallback = new TimesCalledCallback(returnCallback,urls.size());
        Callback<Product> foundProductCallback = (product)->{
            products.add(product);
            productsCallback.call();
        };
        urls.forEach(url->{
            CompletableFuture.runAsync(()->parseProductAsync(url,siteInfo,foundProductCallback,productsCallback));
        });
    }

    private void parseProductAsync(String url,SiteInfo<Product> siteInfo,Callback<Product> foundCallback,NoArgumentsCallback notFoundCallback){
        URI uri = null;
        boolean isUriAppropriate = true;
        try{
            uri = new URI(url);
        }catch (Exception e){
            System.out.println(e.getMessage());
            isUriAppropriate = false;
            notFoundCallback.call();
        }
        if(isUriAppropriate){
            Callback<Product> callback = (product)->{
                product.setSiteName(siteInfo.getSiteName());
                product.setUrl(url);
                foundCallback.call(product);
            };
            classParser.parseAsync(ProductInfoBuilder.fromClone(siteInfo.getClassParseInfo()).setURI(uri).build(),callback);
        }
    }
}
