package com.example.pricechecker.logic.parsing.classParsers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListFilledCallback<T> implements Callback<T>{
    private final Callback<List<T>> filledCallback;
    private final int neededSize;
    private final List<T> list = Collections.synchronizedList(new ArrayList<>());

    public ListFilledCallback(Callback<List<T>> filledCallback,int neededSize) {
        this.filledCallback = filledCallback;
        this.neededSize = neededSize;
    }

    @Override
    public void call(T value) {
        list.add(value);
        if(list.size() == neededSize){
            filledCallback.call(list);
        }
    }
}
