package com.example.pricechecker.logic.parsing.html;

import com.example.pricechecker.model.ClassField;

import java.util.Dictionary;
import java.util.Map;

public record ClassParserInfo<Type>(ClassParser<Type> classParser,
                                    Map<ClassField<?, ?>, PropertyParseInfo> propertyParseInfoDictionary) {
}
