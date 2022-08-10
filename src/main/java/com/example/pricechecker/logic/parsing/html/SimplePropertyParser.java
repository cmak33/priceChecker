package com.example.pricechecker.logic.parsing.html;

import com.example.pricechecker.model.ClassField;
import org.jsoup.select.Elements;

public class SimplePropertyParser<OwnerType,ValueType> extends PropertyParser<OwnerType,ValueType>{
    private final PropertyConverter<ValueType> converter;

    public SimplePropertyParser(ClassField<OwnerType, ValueType> field, PropertyConverter<ValueType> converter) {
        super(field);
        this.converter = converter;
    }

    public void setProperty(OwnerType owner, Elements elements){
        getField().setField(owner,converter.parse(elements));
    }
}
