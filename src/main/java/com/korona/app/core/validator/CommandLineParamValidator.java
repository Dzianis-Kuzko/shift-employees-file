package com.korona.app.core.validator;

import java.util.Set;

public class CommandLineParamValidator {
    private static final Set<String> VALID_SORT_OPTIONS = Set.of("name", "salary");
    private static final Set<String> VALID_ORDER_OPTIONS = Set.of("asc", "desc");
    private static final Set<String> VALID_OUTPUT_OPTIONS = Set.of("console", "file");

    public boolean isValidSortParam(String value) {
        return value != null && VALID_SORT_OPTIONS.contains(value);
    }

    public boolean isValidOrderParam(String value) {
        return value != null && VALID_ORDER_OPTIONS.contains(value);
    }

    public boolean isValidOutputParam(String value) {
        return value != null && VALID_OUTPUT_OPTIONS.contains(value);
    }
}
