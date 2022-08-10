package com.example.pricechecker.logic.parsing.html;

import org.jsoup.select.Elements;

public interface PropertyConverter<ValueType> {
    ValueType parse(Elements elements);
}
