package com.example.pricechecker.model.parseInfo.fieldInfo;

import com.example.pricechecker.logic.collectionConverters.OneElementCollectionConverter;
import com.example.pricechecker.model.ClassField;
import com.example.pricechecker.model.parseInfo.page.OnePageFieldInfo;
import com.example.pricechecker.model.parseInfo.page.PageParseInfo;

public class OneElementFieldInfo<ClassType,FieldType> extends OnePageFieldInfo<ClassType,FieldType> {
    public OneElementFieldInfo(ClassField<ClassType, FieldType> field, PageParseInfo<FieldType> pagesParseInfo) {
        super(field, new OneElementCollectionConverter<>(), pagesParseInfo);
    }
}
