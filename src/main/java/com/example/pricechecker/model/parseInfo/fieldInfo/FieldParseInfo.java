package com.example.pricechecker.model.parseInfo.fieldInfo;

import com.example.pricechecker.logic.collectionConverters.CollectionConverter;
import com.example.pricechecker.model.ClassField;
import com.example.pricechecker.model.parseInfo.page.PageParseInfo;

import java.util.List;
import java.util.Optional;

public class FieldParseInfo<ClassType, FieldType, CollectionType>{
    private final ClassField<ClassType, FieldType> field;
    private final CollectionConverter<FieldType, CollectionType> collectionConverter;
    private final List<PageParseInfo<CollectionType>> pagesParseInfo;

    public FieldParseInfo(FieldParseInfo<ClassType,FieldType,CollectionType> fieldParseInfo){
        this(fieldParseInfo.field,fieldParseInfo.collectionConverter,fieldParseInfo.pagesParseInfo);
    }

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
