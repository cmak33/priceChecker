package com.example.pricechecker.logic.parsing.urlParsers.uriFromStringCreator;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

public class UriFromStringCreator {
    public static Optional<URI> createUri(String stringUri){
        Optional<URI> uri;
        try{
            uri = Optional.of(new URI(stringUri));
        } catch (URISyntaxException uriSyntaxException){
            uri = Optional.empty();
        }
        return uri;
    }
}
