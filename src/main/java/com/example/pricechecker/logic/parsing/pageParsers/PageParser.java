package com.example.pricechecker.logic.parsing.pageParsers;

import java.util.Optional;

public interface PageParser<ValueType>{
    Optional<ValueType> parseValue(String html);
}
