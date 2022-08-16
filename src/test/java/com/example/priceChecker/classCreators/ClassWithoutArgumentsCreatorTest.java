package com.example.priceChecker.classCreators;

import com.example.pricechecker.logic.classCreators.ClassWithoutArgumentsCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ClassWithoutArgumentsCreatorTest {

    public static class NoArgumentsConstructorClass {
    }

    private static class ClassWithoutNoArgumentsConstructor{
        public ClassWithoutNoArgumentsConstructor(int x){}
    }

    @Test
    void create_ClassWithNoArgumentsConstructor_CreateInstance() {
        ClassWithoutArgumentsCreator<NoArgumentsConstructorClass> classCreator = new ClassWithoutArgumentsCreator<>(NoArgumentsConstructorClass.class);
        NoArgumentsConstructorClass instance = classCreator.create();
        Assertions.assertNotNull(instance);
    }

    @Test
    void create_ClassWithoutNoArgumentsConstructor_ThrowsException(){
        Assertions.assertThrows(IllegalArgumentException.class,()->new ClassWithoutArgumentsCreator<>(ClassWithoutNoArgumentsConstructor.class));
    }
}