package com.example.pricechecker.model;

public class ClassField<ClassType, FieldType>{
    private final FieldSetter fieldSetter;
    private final String fieldName;

    public ClassField(FieldSetter fieldSetter, String fieldName, Class<ClassType> ownerType, Class<FieldType> valueType) throws NoSuchFieldException,IllegalArgumentException{
        this.fieldSetter = fieldSetter;
        this.fieldName = fieldName;
        checkIfFieldTypeIsAppropriate(ownerType,valueType,fieldName);
    }

    private void checkIfFieldTypeIsAppropriate(Class<ClassType> ownerType, Class<FieldType> valueType, String fieldName) throws IllegalArgumentException,NoSuchFieldException{
        if(!ownerType.getDeclaredField(fieldName).getType().isAssignableFrom(valueType)){
            throw new IllegalArgumentException();
        }
    }

    public void setField(ClassType owner, FieldType value){
        fieldSetter.setFieldValue(owner,value,fieldName);
    }
}
