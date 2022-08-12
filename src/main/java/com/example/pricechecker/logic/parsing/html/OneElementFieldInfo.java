package com.example.pricechecker.logic.parsing.html;

import com.example.pricechecker.model.ClassField;

public class OneElementFieldInfo<ClassType,FieldType> extends OnePageFieldInfo<ClassType,FieldType>{
    public OneElementFieldInfo(ClassField<ClassType, FieldType> field, PageParseInfo<FieldType> pagesParseInfo) {
        super(field, new OneElementCollectionConverter<>(), pagesParseInfo);
    }
}
