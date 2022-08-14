package com.example.pricechecker.model.parseInfo.classInfo;

import com.example.pricechecker.logic.classCreators.ClassCreator;
import com.example.pricechecker.model.parseInfo.fieldInfo.CompositeFieldParseInfo;
import com.example.pricechecker.model.parseInfo.fieldInfo.FieldParseInfo;

import java.util.ArrayList;
import java.util.List;

public class ClassParseInfo<ClassType> implements Cloneable{
    private ClassCreator<ClassType> classCreator;
    private List<FieldParseInfo<ClassType, ?,?>> fieldParseInfoList;
    private List<CompositeFieldParseInfo<ClassType, ?>> compositeFieldParseInfoList;

    public ClassParseInfo(ClassCreator<ClassType> classCreator){
        this(classCreator,new ArrayList<>(),new ArrayList<>());
    }

    public ClassParseInfo(ClassCreator<ClassType> classCreator, List<FieldParseInfo<ClassType, ?, ?>> fieldParseInfoList, List<CompositeFieldParseInfo<ClassType, ?>> compositeFieldParseInfoList) {
        this.classCreator = classCreator;
        this.fieldParseInfoList = fieldParseInfoList;
        this.compositeFieldParseInfoList = compositeFieldParseInfoList;
    }

    public ClassCreator<ClassType> getClassCreator() {
        return classCreator;
    }

    public List<FieldParseInfo<ClassType, ?, ?>> getFieldParseInfoList() {
        return fieldParseInfoList;
    }

    public List<CompositeFieldParseInfo<ClassType, ?>> getCompositeFieldParseInfoList() {
        return compositeFieldParseInfoList;
    }

    public void setClassCreator(ClassCreator<ClassType> classCreator) {
        this.classCreator = classCreator;
    }

    public void setFieldParseInfoList(List<FieldParseInfo<ClassType, ?, ?>> fieldParseInfoList) {
        this.fieldParseInfoList = fieldParseInfoList;
    }

    public void setCompositeFieldParseInfoList(List<CompositeFieldParseInfo<ClassType, ?>> compositeFieldParseInfoList) {
        this.compositeFieldParseInfoList = compositeFieldParseInfoList;
    }

    @Override
    public ClassParseInfo<ClassType> clone() {
        List<? extends FieldParseInfo<ClassType, ?, ?>> cloneFieldParseInfoList = fieldParseInfoList.stream()
                .map(FieldParseInfo::new).toList();
        List<? extends CompositeFieldParseInfo<ClassType, ?>> cloneCompositeFieldParseInfoList = compositeFieldParseInfoList.stream().map(CompositeFieldParseInfo::new).toList();
        return new ClassParseInfo<>(classCreator,fieldParseInfoList,compositeFieldParseInfoList);
    }
}
