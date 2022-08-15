package com.example.pricechecker.logic.parsing.classParsers;

import com.example.pricechecker.logic.httpRequests.HttpRequestsExecutor;
import com.example.pricechecker.logic.parsing.html.HtmlParser;
import com.example.pricechecker.model.parseInfo.classInfo.ClassParseInfo;
import com.example.pricechecker.model.parseInfo.fieldInfo.CompositeFieldParseInfo;
import com.example.pricechecker.model.parseInfo.fieldInfo.FieldParseInfo;
import com.example.pricechecker.model.parseInfo.page.PageParseInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public record ClassParser(HtmlParser htmlParser,
                          HttpRequestsExecutor executor) {
    public <T> void parseAsync(ClassParseInfo<T> parseInfo,Callback<T> callback){
        T value = parseInfo.getClassCreator().create();
        NoArgumentsCallback setValueCallback = ()->callback.call(value);
        TimesCalledCallback returnCallback = new TimesCalledCallback(setValueCallback,1);
        TimesCalledCallback simpleFieldsCallback = new TimesCalledCallback(returnCallback,parseInfo.getFieldParseInfoList().size());
        TimesCalledCallback compositeFieldsCallback = new TimesCalledCallback(returnCallback,parseInfo.getCompositeFieldParseInfoList().size());
        parseInfo.getFieldParseInfoList().forEach(field->CompletableFuture.runAsync(()->parseSimpleField(value,field)).thenAccept((result)->simpleFieldsCallback.call()));
        parseInfo.getCompositeFieldParseInfoList().forEach(field->parseCompositeFieldAsync(value,field,compositeFieldsCallback));
    }

    private <ClassType,FieldType> void parseCompositeFieldAsync(ClassType owner, CompositeFieldParseInfo<ClassType,FieldType> compositeFieldParseInfo,NoArgumentsCallback callback){
        parseAsync(compositeFieldParseInfo.getClassParseInfo(),(value)->{
            compositeFieldParseInfo.getField().setField(owner,value);
            callback.call();
        });
    }


    public <T> T parse(ClassParseInfo<T> parserInfo) {
        T value = parserInfo.getClassCreator().create();
        parserInfo.getFieldParseInfoList().forEach(field->parseSimpleField(value,field));
        parserInfo.getCompositeFieldParseInfoList().forEach(field->parseCompositeField(value,field));
        return value;
    }

    private <ClassType,FieldType,CollectionType> void parseSimpleField(ClassType owner, FieldParseInfo<ClassType,FieldType,CollectionType> parseInfo){
        List<CollectionType> collection = new ArrayList<>();
        parseInfo.getPagesParseInfo().forEach(info->{
            Optional<CollectionType> parsedPage = parsePage(info);
            parsedPage.ifPresent(collection::add);
        });
        parseInfo.setValue(owner,collection);
    }

    private <T> Optional<T> parsePage(PageParseInfo<T> pageInfo){
        Optional<String> html = executor.receiveHtml(pageInfo.getUri());
        if(html.isPresent()){
            return pageInfo.getPageParser().parseValue(html.get());
        } else{
            return Optional.empty();
        }
    }

    private <ClassType,FieldType> void parseCompositeField(ClassType owner, CompositeFieldParseInfo<ClassType,FieldType> compositeFieldParseInfo){
        FieldType fieldValue = parse(compositeFieldParseInfo.getClassParseInfo());
        compositeFieldParseInfo.getField().setField(owner,fieldValue);
    }
}
