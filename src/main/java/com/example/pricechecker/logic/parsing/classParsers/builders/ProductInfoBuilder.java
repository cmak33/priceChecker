package com.example.pricechecker.logic.parsing.classParsers.builders;

import com.example.pricechecker.logic.classCreators.ClassCreator;
import com.example.pricechecker.model.Product;
import com.example.pricechecker.model.parseInfo.classInfo.ClassParseInfo;

import java.net.URI;

public class ProductInfoBuilder extends ClassParseInfoBuilder<Product>{
    public ProductInfoBuilder(ClassCreator<Product> classCreator) {
        super(classCreator);
    }

    public static ProductInfoBuilder fromClone(ClassParseInfo<Product> productClassParseInfo){
        return new ProductInfoBuilder(productClassParseInfo.clone());
    }

    public ProductInfoBuilder(ClassParseInfo<Product> classParseInfo) {
        super(classParseInfo);
    }

    public ProductInfoBuilder setURI(URI uri){
      build().getFieldParseInfoList().forEach(field->field.getPagesParseInfo().forEach(parseInfo->parseInfo.setUri(uri)));
      return this;
    }
}
