package com.example.pricechecker.logic.classCreators;

public record ClassWithoutArgumentsCreator<T>(Class<T> clazz) implements ClassCreator<T> {
    public ClassWithoutArgumentsCreator(Class<T> clazz) {
        this.clazz = clazz;
        if (!checkIfClassHaveNoArgumentsConstructor(clazz)) {
            throw new IllegalArgumentException("class has not appropriate constructor");
        }
    }

    private boolean checkIfClassHaveNoArgumentsConstructor(Class<T> clazz) {
        boolean constructorExists;
        try {
            constructorExists = clazz.getConstructor().getParameterCount() == 0;
        } catch (NoSuchMethodException exception) {
            constructorExists = false;
        }
        return constructorExists;
    }

    @Override
    public T create() {
        T value;
        try {
            value = clazz.getDeclaredConstructor().newInstance();
        } catch (Exception exception) {
            value = null;
        }
        return value;
    }
}
