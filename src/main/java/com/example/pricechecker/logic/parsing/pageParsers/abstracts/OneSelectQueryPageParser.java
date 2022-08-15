package com.example.pricechecker.logic.parsing.pageParsers.abstracts;

import com.example.pricechecker.logic.parsing.pageParsers.interfaces.PageParser;

public abstract class OneSelectQueryPageParser<T> implements PageParser<T> {
    private final String selectQuery;

    protected OneSelectQueryPageParser(String selectQuery) {
        this.selectQuery = selectQuery;
    }

    protected String getSelectQuery(){
        return selectQuery;
    }
}
