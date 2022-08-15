package com.example.pricechecker.logic.parsing.pageParsers.implemantations;

import com.example.pricechecker.logic.parsing.pageParsers.abstracts.AttributePageParser;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.Optional;

public class OneAttributeFromElementsPageParser extends AttributePageParser<List<String>> {
    public OneAttributeFromElementsPageParser(String selectQuery, String attribute) {
        super(selectQuery, attribute);
    }

    @Override
    public Optional<List<String>> parseValue(String html) {
        Elements elements = Jsoup.parse(html).select(getSelectQuery());
        if(elements.isEmpty()){
            return Optional.empty();
        } else{
            return Optional.of(elements.eachAttr(getAttribute()));
        }
    }
}
