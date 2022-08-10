package com.example.pricechecker.logic.parsing.html;

import com.example.pricechecker.model.ClassField;

public class PropertyParser<OwnerType,ValueType> {
    private final ClassField<OwnerType,ValueType> field;

    public PropertyParser(ClassField<OwnerType, ValueType> field) {
        this.field = field;
    }

    public ClassField<OwnerType, ValueType> getField() {
        return field;
    }
}
