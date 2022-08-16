package com.example.pricechecker.logic.cloning;

import java.util.ArrayList;
import java.util.List;

public class ListCloner {
    public static <T> List<T> cloneList(List<? extends Cloneable<T>> list){
        List<T> cloneList = new ArrayList<>();
        list.forEach(element->cloneList.add(element.cloneObject()));
        return cloneList;
    }
}
