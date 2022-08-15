package com.example.pricechecker.model.parseInfo.fieldInfo;

import com.example.pricechecker.logic.collectionConverters.CollectionConverter;
import com.example.pricechecker.model.ClassField;
import com.example.pricechecker.model.parseInfo.pageInfo.PageParseInfo;

import java.util.List;

public class OnePageFieldInfo<ClassType,FieldType> extends FieldParseInfo<ClassType,FieldType,FieldType> {
    public OnePageFieldInfo(ClassField<ClassType, FieldType> field, CollectionConverter<FieldType, FieldType> collectionConverter, PageParseInfo<FieldType> pagesParseInfo) {
        super(field, collectionConverter, List.of(pagesParseInfo));
    }
}
