package com.example.pricechecker.logic.parsing.pageParsers.implemantations;

import com.example.pricechecker.logic.parsing.pageParsers.abstracts.OneSelectQueryPageParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.util.Optional;

public class TextPageParser extends OneSelectQueryPageParser<String> {
    public TextPageParser(String selectQuery) {
        super(selectQuery);
    }

    @Override
    public Optional<String> parseValue(String html) {
        Element element = Jsoup.parse(html).selectFirst(getSelectQuery());
        if(element!=null){
            return Optional.of(element.text());
        } else{
            return Optional.empty();
        }
    }
}
