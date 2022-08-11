package com.example.pricechecker.logic.parsing.html;

import com.example.pricechecker.model.ClassField;

public record CompositeFieldParseInfo<ClassType, FieldType>(
        ClassField<ClassType, FieldType> field,
        ClassParseInfo<FieldType> classParseInfo) {
}
