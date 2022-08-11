package com.example.pricechecker.logic.parsing.html;

import com.example.pricechecker.model.ClassField;
import org.jsoup.select.Elements;

public record FieldFromElementsSetter<ClassType, FieldType>(
        ClassField<ClassType, FieldType> classField,
        ElementsConverter<FieldType> elementsConverter) {
    public void setFromElements(ClassType owner, Elements elements){
        FieldType fieldValue = elementsConverter.convert(elements);
        classField.setField(owner,fieldValue);
    }
}
