package com.example.pricechecker.logic.parsing.html;

import java.util.List;
import java.util.Optional;

public interface CollectionConverter<ValueType,CollectionType>{
    Optional<ValueType> convert(List<CollectionType> collection);
}
