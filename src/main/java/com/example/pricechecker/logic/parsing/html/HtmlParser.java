package com.example.pricechecker.logic.parsing.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlParser {
    public String getElementAttribute(Element element,String attribute){
        return element.attr(attribute);
    }

    public Elements findElementsByQuery(String html,String selectQuery){
        Document document = Jsoup.parse(html);
        return document.select(selectQuery);
    }
}
