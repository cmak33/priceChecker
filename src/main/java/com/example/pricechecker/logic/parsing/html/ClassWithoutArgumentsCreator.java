package com.example.pricechecker.logic.parsing.html;

public class ClassWithoutArgumentsCreator<T> implements ClassCreator<T>{
    private final Class<T> clazz;

    public ClassWithoutArgumentsCreator(Class<T> clazz) throws IllegalArgumentException {
        this.clazz = clazz;
        if(!checkIfClassHaveNoArgumentsConstructor(clazz)){
           throw new IllegalArgumentException("class has not appropriate constructor");
        }
    }

    private boolean checkIfClassHaveNoArgumentsConstructor(Class<T> clazz){
        boolean constructorExists;
        try{
            constructorExists = clazz.getConstructor().getParameterCount() == 0;
        } catch (NoSuchMethodException exception){
            constructorExists = false;
        }
        return constructorExists;
    }

    @Override
    public T create() {
        T value;
        try{
            value = clazz.getDeclaredConstructor().newInstance();
        } catch (Exception exception){
            value = null;
        }
        return value;
    }
}
