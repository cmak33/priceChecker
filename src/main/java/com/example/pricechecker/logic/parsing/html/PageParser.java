package com.example.pricechecker.logic.parsing.html;

import java.util.Optional;

public interface PageParser<ValueType>{
    Optional<ValueType> parseValue(String html);
}
