package com.example.pricechecker.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class FieldSetter {
    private final static Logger logger = LoggerFactory.getLogger(FieldSetter.class);

    public void setFieldValue(Object obj,Object value,String fieldName){
        Class<?> objClass = obj.getClass();
        try{
            Field field = objClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj,value);
        } catch (IllegalAccessException | NoSuchFieldException fieldException){
            logger.error("could not set such field to object",fieldException);
        }
    }
}
