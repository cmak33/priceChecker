package com.example.pricechecker.model.parseInfo.fieldInfo;

import com.example.pricechecker.logic.collectionConverters.CollectionConverter;
import com.example.pricechecker.model.ClassField;
import com.example.pricechecker.model.parseInfo.pageInfo.PageParseInfo;

import java.util.List;

public class OnePageFieldInfo<ClassType,FieldType,CollectionType> extends FieldParseInfo<ClassType,FieldType,CollectionType> {
    public OnePageFieldInfo(ClassField<ClassType, FieldType> field, CollectionConverter<FieldType, CollectionType> collectionConverter, PageParseInfo<CollectionType> pagesParseInfo) {
        super(field, collectionConverter, List.of(pagesParseInfo));
    }
}
