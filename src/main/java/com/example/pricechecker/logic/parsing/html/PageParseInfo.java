package com.example.pricechecker.logic.parsing.html;

import java.net.URI;

public record PageParseInfo<ValueType>(URI uri,
                                       PageParser<ValueType> pageParser) {
}
