package com.example.pricechecker.logic.parsing.urlParsers;

import com.example.pricechecker.logic.httpRequests.HttpRequestsExecutor;
import com.example.pricechecker.logic.parsing.pageParsers.interfaces.PageParser;
import com.example.pricechecker.logic.parsing.urlParsers.uriFromStringCreator.UriFromStringCreator;
import com.example.pricechecker.logic.parsing.urlParsers.urlCreators.UrlCreatorByPageNumber;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record UrlParser(HttpRequestsExecutor requestsExecutor) {

    public List<String> findItemsUrls(UrlCreatorByPageNumber urlCreator, int maxUrlsCount, PageParser<List<String>> pageParser) {
        List<String> urls = new ArrayList<>();
        int currentPage = 1;
        boolean shouldContinue;
        do {
            shouldContinue = false;
            String url = urlCreator.createUrl(currentPage);
            Optional<URI> uri = UriFromStringCreator.createUri(url);
            if(uri.isPresent()){
                Optional<List<String>> foundUrls = findUrlsByUri(uri.get(),pageParser);
                if(foundUrls.isPresent()){
                    urls.addAll(foundUrls.get());
                    shouldContinue = urls.size()<maxUrlsCount;
                }
            }
            currentPage++;
        } while (shouldContinue);
        return urls;
    }

    private Optional<List<String>> findUrlsByUri(URI uri,PageParser<List<String>> pageParser){
        Optional<List<String>> foundUrls;
        Optional<String> html = requestsExecutor.receiveHtml(uri);
        if (html.isPresent()) {
            foundUrls = pageParser.parseValue(html.get());
        } else{
            foundUrls = Optional.empty();
        }
        return foundUrls;
    }
}
