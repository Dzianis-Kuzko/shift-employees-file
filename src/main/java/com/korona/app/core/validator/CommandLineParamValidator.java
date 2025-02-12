package com.korona.app.core.validator;

import java.util.Set;

public class CommandLineParamValidator {
    private static final Set<String> VALID_SORT_OPTIONS = Set.of("name", "salary");
    private static final Set<String> VALID_ORDER_OPTIONS = Set.of("asc", "desc");
    private static final Set<String> VALID_OUTPUT_OPTIONS = Set.of("console", "file");

    public boolean isValidSortParam(String value) {
        if (value != null && VALID_SORT_OPTIONS.contains(value)) {
            return true;
        } else {
            throw new IllegalArgumentException("Некорректное значение сортировки: " + value);
        }
    }


    public boolean isValidOrderParam(String value) {
        if (value != null && VALID_ORDER_OPTIONS.contains(value)) {
            return true;
        } else {
            throw new IllegalArgumentException("Некорректное значение порядка сортировки: " + value);
        }
    }
}
