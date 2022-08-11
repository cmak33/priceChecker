package com.example.pricechecker.logic.parsing.html;

import com.example.pricechecker.logic.httpRequests.HttpRequestsExecutor;
import org.jsoup.select.Elements;

import java.net.http.HttpResponse;
import java.util.Optional;

public record ClassParser(HtmlParser htmlParser,
                          HttpRequestsExecutor executor) {
    public <T> T parse(ClassParseInfo<T> parserInfo) {
        T value = parserInfo.classCreator().create();
        parserInfo.fieldParseInfoList().forEach(fieldParseInfo->parseSimpleField(value,fieldParseInfo));
        parserInfo.compositeFieldParseInfoList().forEach(fieldParseInfo->parseCompositeField(value,fieldParseInfo));
        return value;
    }

    private <ClassType,FieldType> void parseSimpleField(ClassType owner, FieldParseInfo<ClassType,FieldType> parseInfo){
        Elements elements = findElements(parseInfo.elementsSelectionInfo());
        parseInfo.fieldFromElementsSetter().setFromElements(owner,elements);
    }

    private Elements findElements(ElementsSelectionInfo elementsSelectionInfo){
        Elements elements;
        Optional<HttpResponse<String>> response = executor.executeRequestSync(elementsSelectionInfo.uri(), HttpResponse.BodyHandlers.ofString());
        if(response.isPresent()){
            elements =htmlParser.findElementsByQuery(response.get().body(),elementsSelectionInfo.selectQuery());
        } else{
            elements = new Elements();
        }
        return elements;
    }

    private <ClassType,FieldType> void parseCompositeField(ClassType owner, CompositeFieldParseInfo<ClassType,FieldType> compositeFieldParseInfo){
        FieldType fieldValue = parse(compositeFieldParseInfo.classParseInfo());
        compositeFieldParseInfo.field().setField(owner,fieldValue);
    }
}
