package com.example.pricechecker.logic.parsing.html;

import java.util.List;

public record ClassParseInfo<ClassType>(
        ClassCreator<ClassType> classCreator,
        List<FieldParseInfo<ClassType, ?,?>> fieldParseInfoList,
        List<CompositeFieldParseInfo<ClassType, ?>> compositeFieldParseInfoList) {
}
