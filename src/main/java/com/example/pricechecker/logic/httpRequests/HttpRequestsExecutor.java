package com.example.pricechecker.logic.httpRequests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class HttpRequestsExecutor {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestsExecutor.class);

    private interface SendMethod<R,T>{
        R send(HttpClient httpClient,HttpRequest httpRequest,BodyHandler<T> bodyHandler) throws InterruptedException,IOException;
    }

    public Optional<String> receiveHtml(URI uri){
        return executeRequestBySendMethod(uri, HttpResponse.BodyHandlers.ofString()).map(HttpResponse::body);
    }

    public <T> Optional<HttpResponse<T>> executeRequestBySendMethod(URI uri, BodyHandler<T> bodyHandler){
        SendMethod<HttpResponse<T>,T> sendMethod = HttpClient::send;
        return executeRequestBySendMethod(uri,sendMethod,bodyHandler);
    }

    public <T> Optional<CompletableFuture<HttpResponse<T>>> executeRequestAsync(URI uri,BodyHandler<T> bodyHandler){
        SendMethod<CompletableFuture<HttpResponse<T>>,T> sendMethod = HttpClient::sendAsync;
        return executeRequestBySendMethod(uri,sendMethod,bodyHandler);
    }

    private <R,T> Optional<R> executeRequestBySendMethod(URI uri, SendMethod<R,T> sendMethod, BodyHandler<T> bodyHandler){
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder(uri).GET().build();
        Optional<R> result;
        try {
            result = Optional.of(sendMethod.send(httpClient,httpRequest,bodyHandler));
        } catch (InterruptedException | IOException exception){
            result = Optional.empty();
            logger.warn("exception sending http request",exception);
        }
        return result;
    }
}
