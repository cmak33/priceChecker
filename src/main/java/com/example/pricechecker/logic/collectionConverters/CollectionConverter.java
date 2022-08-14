package com.example.pricechecker.logic.collectionConverters;

import java.util.List;
import java.util.Optional;

public interface CollectionConverter<ValueType,CollectionType>{
    Optional<ValueType> convert(List<CollectionType> collection);
}
