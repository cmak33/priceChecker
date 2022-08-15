package com.example.pricechecker.logic.classCreators;

public record ClassWithoutArgumentsCreator<T>(Class<T> clazz) implements ClassCreator<T> {
    public ClassWithoutArgumentsCreator{
        if (!hasNoArgumentsConstructor(clazz)){
            throw new IllegalArgumentException("class has not appropriate constructor");
        }
    }

    private boolean hasNoArgumentsConstructor(Class<T> clazz) {
        boolean hasConstructor;
        try {
            hasConstructor = clazz.getConstructor().getParameterCount() == 0;
        } catch (NoSuchMethodException exception) {
            hasConstructor = false;
        }
        return hasConstructor;
    }

    @Override
    public T create() {
        T value;
        try {
            value = clazz.getDeclaredConstructor().newInstance();
        } catch (Exception noSuchMethodException) {
            value = null;
        }
        return value;
    }
}
