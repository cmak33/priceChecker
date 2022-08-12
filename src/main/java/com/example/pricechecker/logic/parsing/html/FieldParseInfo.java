package com.example.pricechecker.logic.parsing.html;

import com.example.pricechecker.model.ClassField;

import java.util.List;
import java.util.Optional;

public class FieldParseInfo<ClassType, FieldType, CollectionType>{
    private final ClassField<ClassType, FieldType> field;
    private final CollectionConverter<FieldType, CollectionType> collectionConverter;
    private final List<PageParseInfo<CollectionType>> pagesParseInfo;

    public FieldParseInfo(ClassField<ClassType, FieldType> field, CollectionConverter<FieldType, CollectionType> collectionConverter, List<PageParseInfo<CollectionType>> pagesParseInfo) {
        this.field = field;
        this.collectionConverter = collectionConverter;
        this.pagesParseInfo = pagesParseInfo;
    }

    public void setValue(ClassType owner, List<CollectionType> collection){
        Optional<FieldType> fieldValue = collectionConverter.convert(collection);
        fieldValue.ifPresent(value->field.setField(owner,value));
    }

    public List<PageParseInfo<CollectionType>> getPagesParseInfo() {
        return pagesParseInfo;
    }
}
