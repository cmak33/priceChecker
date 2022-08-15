package com.example.pricechecker.logic.collectionConverters;

import java.util.List;
import java.util.Optional;

public class FractionalNumberToLongConverter implements CollectionConverter<Long,String>{
    @Override
    public Optional<Long> convert(List<String> collection) {
        if(!collection.isEmpty()){
            String priceString = collection.get(0).split("\\.")[0];
            Long price = Long.parseLong(priceString);
            return Optional.of(price);
        } else{
            return Optional.empty();
        }
    }
}
