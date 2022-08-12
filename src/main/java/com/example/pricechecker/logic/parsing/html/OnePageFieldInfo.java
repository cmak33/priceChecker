package com.example.pricechecker.logic.parsing.html;

import com.example.pricechecker.model.ClassField;

import java.util.List;

public class OnePageFieldInfo<ClassType,FieldType> extends FieldParseInfo<ClassType,FieldType,FieldType>{
    public OnePageFieldInfo(ClassField<ClassType, FieldType> field, CollectionConverter<FieldType, FieldType> collectionConverter, PageParseInfo<FieldType> pagesParseInfo) {
        super(field, collectionConverter, List.of(pagesParseInfo));
    }
}
