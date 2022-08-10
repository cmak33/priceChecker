package com.example.pricechecker.logic.parsing.html;

import com.example.pricechecker.model.ClassField;

public class CompositePropertyParser<OwnerType,ValueType> extends PropertyParser<OwnerType,ValueType>{
    private final ClassParser<ValueType> classParser;
    private final ClassCreator<ValueType> classCreator;

    public CompositePropertyParser(ClassField<OwnerType, ValueType> field, ClassParser<ValueType> classParser, ClassCreator<ValueType> classCreator) {
        super(field);
        this.classParser = classParser;
        this.classCreator = classCreator;
    }

    public ClassParser<ValueType> getClassParser() {
        return classParser;
    }

    public void setValue(OwnerType owner,ValueType value){
        getField().setField(owner, value);
    }

    public ValueType getEmptyValue(){
        return classCreator.create();
    }
}
