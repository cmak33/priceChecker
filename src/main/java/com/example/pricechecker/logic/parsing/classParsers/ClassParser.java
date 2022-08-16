package com.example.pricechecker.logic.parsing.classParsers;

import com.example.pricechecker.logic.callbacks.NoArgumentsCallback;
import com.example.pricechecker.logic.callbacks.TimesCalledCallback;
import com.example.pricechecker.logic.httpRequests.HttpRequestsExecutor;
import com.example.pricechecker.model.parseInfo.classInfo.ClassParseInfo;
import com.example.pricechecker.model.parseInfo.fieldInfo.CompositeFieldParseInfo;
import com.example.pricechecker.model.parseInfo.fieldInfo.FieldParseInfo;
import com.example.pricechecker.model.parseInfo.pageInfo.PageParseInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public record ClassParser(HttpRequestsExecutor executor) {
    public <T> CompletableFuture<T> parseAsync(ClassParseInfo<T> parseInfo){
        CompletableFuture<T> result = new CompletableFuture<>();
        T value = parseInfo.getClassCreator().create();
        NoArgumentsCallback completeResultValueCompletableFuture = ()->result.complete(value);
        int fieldsCount = calculateFieldsCount(parseInfo);
        TimesCalledCallback setFieldCallback = new TimesCalledCallback(completeResultValueCompletableFuture,fieldsCount);
        parseInfo.getFieldParseInfoList()
                .forEach(field->parseSimpleFieldAsync(value,field,setFieldCallback));
        parseInfo.getCompositeFieldParseInfoList()
                .forEach(field->parseCompositeFieldAsync(value,field,setFieldCallback));
        return result;
    }

    private <T> int calculateFieldsCount(ClassParseInfo<T> classParseInfo){
        return classParseInfo.getFieldParseInfoList().size() + classParseInfo.getCompositeFieldParseInfoList().size();
    }

    private <ClassType,FieldType,CollectionType> void parseSimpleFieldAsync(ClassType owner, FieldParseInfo<ClassType,FieldType,CollectionType> parseInfo,NoArgumentsCallback callback){
        CompletableFuture
                .runAsync(()->parseSimpleField(owner,parseInfo))
                .thenAccept((result)->callback.call());
    }

    private <ClassType,FieldType> void parseCompositeFieldAsync(ClassType owner, CompositeFieldParseInfo<ClassType,FieldType> compositeFieldParseInfo,NoArgumentsCallback callback){
        parseAsync(compositeFieldParseInfo.getClassParseInfo())
                .thenAccept((value)->{
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
