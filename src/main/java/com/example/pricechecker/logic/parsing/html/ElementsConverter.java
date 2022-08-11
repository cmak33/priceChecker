package com.example.pricechecker.logic.parsing.html;

import org.jsoup.select.Elements;

public interface ElementsConverter<ValueType> {
    ValueType convert(Elements elements);
}
