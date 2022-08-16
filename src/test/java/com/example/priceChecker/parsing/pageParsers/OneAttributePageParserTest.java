package com.example.priceChecker.parsing.pageParsers;

import com.example.pricechecker.logic.parsing.pageParsers.implemantations.OneAttributePageParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OneAttributePageParserTest {

    @Test
    void parseValue_HtmlHasNeededElement_ReturnValue() {
        String value = "value";
        String html = String.format("<span content='%s'></span><span content='content'></span><p></p>",value);
        OneAttributePageParser pageParser = new OneAttributePageParser("span","content");

        Optional<String> expected = Optional.of(value);
        Optional<String> actual = pageParser.parseValue(html);

        Assertions.assertEquals(expected,actual);
    }

    @Test
    void parseValue_HtmlHasNotNeededElement_ReturnEmptyOptional(){
        String html = "";
        OneAttributePageParser pageParser = new OneAttributePageParser("span","content");

        Optional<String> expected = Optional.empty();
        Optional<String> actual = pageParser.parseValue(html);

        Assertions.assertEquals(expected,actual);
    }
}