package com.example.pricechecker.logic.parsing.html;

import java.util.List;

public record ClassParser<Type>(
        List<SimplePropertyParser<Type, ?>> simplePropertyParsers,
        List<CompositePropertyParser<Type, ?>> compositePropertyParsers) {
    public List<SimplePropertyParser<Type, ?>> getSimplePropertyParsers() {
        return simplePropertyParsers;
    }

    public List<CompositePropertyParser<Type, ?>> getCompositePropertyParsers() {
        return compositePropertyParsers;
    }

}
