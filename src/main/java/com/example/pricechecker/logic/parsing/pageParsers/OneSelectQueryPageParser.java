package com.example.pricechecker.logic.parsing.pageParsers;

public abstract class OneSelectQueryPageParser<T> implements PageParser<T>{
    private final String selectQuery;

    protected OneSelectQueryPageParser(String selectQuery) {
        this.selectQuery = selectQuery;
    }

    protected String getSelectQuery(){
        return selectQuery;
    }
}
