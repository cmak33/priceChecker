package com.example.pricechecker.logic.callbacks;

public class TimesCalledCallback implements NoArgumentsCallback{
    private final NoArgumentsCallback callback;
    private final int neededCount;
    private int currentCount = 0;

    public TimesCalledCallback(NoArgumentsCallback callback, int neededCount) {
        this.callback = callback;
        this.neededCount = neededCount;
    }

    @Override
    public void call() {
        currentCount++;
        if(currentCount==neededCount){
            callback.call();
        }
    }
}
