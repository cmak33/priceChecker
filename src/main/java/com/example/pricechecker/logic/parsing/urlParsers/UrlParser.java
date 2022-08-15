package com.example.pricechecker.logic.parsing.urlParsers;

import com.example.pricechecker.logic.httpRequests.HttpRequestsExecutor;
import com.example.pricechecker.logic.parsing.pageParsers.interfaces.PageParser;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UrlParser {
    private final HttpRequestsExecutor requestsExecutor;

    public UrlParser(HttpRequestsExecutor requestsExecutor) {
        this.requestsExecutor = requestsExecutor;
    }

    // fix this please
    public List<String> findItemsUrls(String name,String pageUrlFormat, int maxUrlsCount, PageParser<List<String>> pageParser){
        List<String> urls = new ArrayList<>();
        int currentPage = 1;
        String currentUrl;
        Optional<String> html;
        Optional<List<String>> foundUrls = Optional.empty();
        do{
            currentUrl = String.format(pageUrlFormat,name,currentPage);
            try {
                html = requestsExecutor.receiveHtml(new URI(currentUrl));
                if(html.isPresent()){
                    foundUrls = pageParser.parseValue(html.get());
                    if(foundUrls.isPresent()){
                        urls.addAll(foundUrls.get());
                    }
                }
            } catch (URISyntaxException uriSyntaxException){
                html = Optional.empty();
            }
            currentPage++;
        } while(html.isPresent() && foundUrls.isPresent() && !foundUrls.get().isEmpty() && urls.size()<maxUrlsCount);
        return urls;
    }





}
