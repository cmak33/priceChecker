package com.example.pricechecker.model;

public class ClassField<OwnerType,ValueType>{
    private final FieldSetter fieldSetter;
    private final String fieldName;
    private final Class<OwnerType> ownerType;
    private final Class<ValueType> valueType;

    public ClassField(FieldSetter fieldSetter, String fieldName, Class<OwnerType> ownerType,Class<ValueType> valueType) throws NoSuchFieldException,IllegalArgumentException{
        this.fieldSetter = fieldSetter;
        this.fieldName = fieldName;
        this.ownerType = ownerType;
        this.valueType = valueType;
        checkIfFieldTypeIsAppropriate(ownerType,valueType,fieldName);
    }

    private void checkIfFieldTypeIsAppropriate(Class<OwnerType> ownerType,Class<ValueType> valueType,String fieldName) throws IllegalArgumentException,NoSuchFieldException{
        if(!ownerType.getDeclaredField(fieldName).getType().isAssignableFrom(valueType)){
            throw new IllegalArgumentException();
        }
    }

    public void setField(OwnerType owner,ValueType value){
        fieldSetter.setFieldValue(owner,value,fieldName);
    }

    public String getFieldName() {
        return fieldName;
    }

    public Class<OwnerType> getOwnerType() {
        return ownerType;
    }

    public Class<ValueType> getValueType() {
        return valueType;
    }
}
