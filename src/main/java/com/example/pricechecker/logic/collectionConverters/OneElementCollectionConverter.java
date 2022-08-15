package com.example.pricechecker.logic.collectionConverters;

import java.util.List;
import java.util.Optional;

public class OneElementCollectionConverter<T> implements CollectionConverter<T,T> {
    @Override
    public Optional<T> convert(List<T> collection) {
        Optional<T> result;
        if(collection.isEmpty()){
            result = Optional.empty();
        } else{
            T firstElement = collection.get(0);
            result = Optional.of(firstElement);
        }
        return result;
    }
}
