package com.example.pricechecker.model;

import com.example.pricechecker.logic.parsing.classParsers.ClassParser;
import com.example.pricechecker.logic.parsing.classParsers.builders.ProductInfoBuilder;
import com.example.pricechecker.logic.parsing.pageParsers.UrlParser;
import com.example.pricechecker.model.parseInfo.classInfo.ClassParseInfo;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductParser {
    private final ClassParser classParser;
    private final UrlParser urlParser;


    public ProductParser(ClassParser classParser,UrlParser urlParser) {
        this.classParser = classParser;
        this.urlParser = urlParser;
    }

    public List<Product> parseProducts(String productName,int maxPerSite, List<SiteInfo<Product>> siteInfoList){
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
}
