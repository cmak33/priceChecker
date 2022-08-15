package com.example.pricechecker.logic.parsing.pageParsers.abstracts;

public abstract class AttributePageParser<T> extends OneSelectQueryPageParser<T> {
    private final String attribute;

    protected AttributePageParser(String selectQuery, String attribute) {
        super(selectQuery);
        this.attribute = attribute;
    }

    protected String getAttribute() {
        return attribute;
    }
}
