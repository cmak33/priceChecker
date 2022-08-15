package com.example.pricechecker.model;

public class ClassField<ClassType, FieldType>{
    private final FieldSetter fieldSetter;
    private final String fieldName;

    public ClassField(FieldSetter fieldSetter, String fieldName, Class<ClassType> ownerType, Class<FieldType> valueType){
        if(!isFieldTypeAppropriate(ownerType,valueType,fieldName)){
            throw new IllegalArgumentException();
        }
        this.fieldSetter = fieldSetter;
        this.fieldName = fieldName;
    }

    private boolean isFieldTypeAppropriate(Class<ClassType> ownerType, Class<FieldType> valueType, String fieldName){
        boolean isAppropriate;
        try{
            isAppropriate = ownerType.getDeclaredField(fieldName).getType().isAssignableFrom(valueType);
        } catch (NoSuchFieldException noSuchFieldException){
            isAppropriate = false;
        }
        return isAppropriate;
    }

    public void setField(ClassType owner, FieldType value){
        fieldSetter.setFieldValue(owner,value,fieldName);
    }
}
