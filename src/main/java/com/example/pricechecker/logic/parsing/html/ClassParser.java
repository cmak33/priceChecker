package com.example.pricechecker.logic.parsing.html;

import com.example.pricechecker.logic.httpRequests.HttpRequestsExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record ClassParser(HtmlParser htmlParser,
                          HttpRequestsExecutor executor) {
    public <T> T parse(ClassParseInfo<T> parserInfo) {
        T value = parserInfo.classCreator().create();
        parserInfo.fieldParseInfoList().forEach(fieldParseInfo->parseSimpleField(value,fieldParseInfo));
        parserInfo.compositeFieldParseInfoList().forEach(fieldParseInfo->parseCompositeField(value,fieldParseInfo));
        return value;
    }

    private <ClassType,FieldType,CollectionType> void parseSimpleField(ClassType owner, FieldParseInfo<ClassType,FieldType,CollectionType> parseInfo){
        List<CollectionType> pagesParseValues = new ArrayList<>();
        parseInfo.pagesParseInfo().forEach(info-> parsePage(info).ifPresent(pagesParseValues::add));
        parseInfo.setValue(owner,pagesParseValues);
    }

    private <T> Optional<T> parsePage(PageParseInfo<T> pageInfo){
        Optional<String> html = executor.receiveHtml(pageInfo.uri());
        if(html.isPresent()){
            return pageInfo.pageParser().parseValue(html.get());
        } else{
            return Optional.empty();
        }
    }

    private <ClassType,FieldType> void parseCompositeField(ClassType owner, CompositeFieldParseInfo<ClassType,FieldType> compositeFieldParseInfo){
        FieldType fieldValue = parse(compositeFieldParseInfo.classParseInfo());
        compositeFieldParseInfo.field().setField(owner,fieldValue);
    }
}
