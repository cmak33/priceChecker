package com.example.pricechecker.logic.parsing.html;

import com.example.pricechecker.logic.httpRequests.HttpRequestsExecutor;
import com.example.pricechecker.model.ClassField;
import org.jsoup.select.Elements;

import java.net.http.HttpResponse;
import java.util.Dictionary;
import java.util.Map;
import java.util.Optional;

public record ParserManager(HtmlParser htmlParser,
                            HttpRequestsExecutor executor) {
    public <T> void parse(ClassParserInfo<T> parserInfo,T emptyClass) {
        parserInfo.classParser().getSimplePropertyParsers().forEach(field->parseSimpleField(emptyClass,parserInfo.propertyParseInfoDictionary(),field));
        parserInfo.classParser().getCompositePropertyParsers().forEach(field->parseCompositeField(emptyClass,parserInfo.propertyParseInfoDictionary(),field));
    }

    private <T> void parseSimpleField(T owner, Map<ClassField<?, ?>, PropertyParseInfo> dictionary, SimplePropertyParser<T,?> parser){
       PropertyParseInfo info = dictionary.get(parser.getField());
       if(info!=null) {
           Elements elements = getElements(info);
           parser.setProperty(owner,elements);
       }
    }

    private Elements getElements(PropertyParseInfo info){
        Elements elements;
        Optional<HttpResponse<String>> response = executor.executeRequestSync(info.uri(), HttpResponse.BodyHandlers.ofString());
        if(response.isPresent()){
            elements =htmlParser.findElementsByQuery(response.get().body(),info.selectQuery());
        } else{
            elements = new Elements();
        }
        return elements;
    }

    private <T,V> void parseCompositeField(T owner,Map<ClassField<?, ?>, PropertyParseInfo> dictionary,CompositePropertyParser<T,V> compositePropertyParser){
        V value = compositePropertyParser.getEmptyValue();
        compositePropertyParser.setValue(owner,value);
        ClassParserInfo<V> info = new ClassParserInfo<>(compositePropertyParser.getClassParser(),dictionary);
        parse(info,value);
    }
}
