package com.example.pricechecker.model.parseInfo.fieldInfo;

import com.example.pricechecker.logic.cloning.Cloneable;
import com.example.pricechecker.model.ClassField;
import com.example.pricechecker.model.parseInfo.classInfo.ClassParseInfo;

public class CompositeFieldParseInfo<ClassType, FieldType> implements Cloneable<CompositeFieldParseInfo<ClassType, FieldType>> {
    private final ClassField<ClassType, FieldType> field;
    private final ClassParseInfo<FieldType> classParseInfo;

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

    @Override
    public CompositeFieldParseInfo<ClassType, FieldType> cloneObject() {
        return new CompositeFieldParseInfo<>(field,classParseInfo.cloneObject());
    }
}
