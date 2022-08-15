package com.example.pricechecker.logic.parsing.urlParsers.urlCreators;

public record UrlCreatorByNameAndPageNumber(String urlFormat,String name) implements UrlCreatorByPageNumber {
    @Override
    public String createUrl(int pageNumber) {
        return String.format(urlFormat,name,pageNumber);
    }
}
