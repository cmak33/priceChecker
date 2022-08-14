package com.example.pricechecker.logic.collectionConverters;

import java.util.List;
import java.util.Optional;

public class OneElementCollectionConverter<T> implements CollectionConverter<T,T> {
    @Override
    public Optional<T> convert(List<T> collection) {
        return collection.isEmpty()?Optional.empty():Optional.of(collection.get(0));
    }
}
