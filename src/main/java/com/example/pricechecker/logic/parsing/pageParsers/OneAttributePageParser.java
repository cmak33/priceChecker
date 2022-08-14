package com.example.pricechecker.logic.parsing.pageParsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.util.Optional;

public class OneAttributePageParser extends AttributePageParser<String>{
    public OneAttributePageParser(String selectQuery, String attribute) {
        super(selectQuery, attribute);
    }

    @Override
    public Optional<String> parseValue(String html) {
        Element element = Jsoup.parse(html).selectFirst(getSelectQuery());
        if(element!=null && element.hasAttr(getAttribute())){
            return Optional.of(element.attr(getAttribute()));
        } else{
            return Optional.empty();
        }
    }
}
