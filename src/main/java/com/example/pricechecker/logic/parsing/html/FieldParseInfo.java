package com.example.pricechecker.logic.parsing.html;

public record   FieldParseInfo<ClassType, FieldType>(
        FieldFromElementsSetter<ClassType, FieldType> fieldFromElementsSetter,
        ElementsSelectionInfo elementsSelectionInfo) {
}
