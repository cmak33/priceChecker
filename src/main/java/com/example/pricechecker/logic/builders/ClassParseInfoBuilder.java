package com.example.pricechecker.logic.builders;

import com.example.pricechecker.logic.classCreators.ClassCreator;
import com.example.pricechecker.logic.classCreators.ClassWithoutArgumentsCreator;
import com.example.pricechecker.model.parseInfo.classInfo.ClassParseInfo;
import com.example.pricechecker.model.parseInfo.fieldInfo.CompositeFieldParseInfo;
import com.example.pricechecker.model.parseInfo.fieldInfo.FieldParseInfo;

public class ClassParseInfoBuilder<T>{
    private final ClassParseInfo<T> classParseInfo;

    public ClassParseInfoBuilder(Class<T> clazz){
        this(new ClassWithoutArgumentsCreator<>(clazz));
    }

    public ClassParseInfoBuilder(ClassCreator<T> classCreator){
        classParseInfo = new ClassParseInfo<>(classCreator);
    }

    public ClassParseInfoBuilder(ClassParseInfo<T> classParseInfo){
        this.classParseInfo = classParseInfo;
    }

    public  static <A> ClassParseInfoBuilder<A> createBuilderWithClone(ClassParseInfo<A> classParseInfo){
        return new ClassParseInfoBuilder<A>(classParseInfo.cloneObject());
    }

    public ClassParseInfoBuilder<T> addFieldInfo(FieldParseInfo<T,?,?> fieldParseInfo){
        classParseInfo.getFieldParseInfoList().add(fieldParseInfo);
        return this;
    }

    public ClassParseInfoBuilder<T> addCompositeField(CompositeFieldParseInfo<T,?> compositeFieldParseInfo){
        classParseInfo.getCompositeFieldParseInfoList().add(compositeFieldParseInfo);
        return this;
    }

    public ClassParseInfo<T> build(){
        return classParseInfo;
    }
}
