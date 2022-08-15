package com.example.pricechecker.model.parseInfo.fieldInfo;

import com.example.pricechecker.model.ClassField;
import com.example.pricechecker.model.parseInfo.classInfo.ClassParseInfo;

public class CompositeFieldParseInfo<ClassType, FieldType>{
    private final ClassField<ClassType, FieldType> field;
    private final ClassParseInfo<FieldType> classParseInfo;

    public CompositeFieldParseInfo(CompositeFieldParseInfo<ClassType,FieldType> compositeFieldParseInfo){
        this(compositeFieldParseInfo.field,compositeFieldParseInfo.classParseInfo.clone());
    }

    public CompositeFieldParseInfo(ClassField<ClassType, FieldType> field, ClassParseInfo<FieldType> classParseInfo) {
        this.field = field;
        this.classParseInfo = classParseInfo;
    }

    public ClassField<ClassType, FieldType> getField() {
        return field;
    }

    public ClassParseInfo<FieldType> getClassParseInfo() {
        return classParseInfo;
    }
}
