package com.korona.app.api.controller.option.impl;

import com.korona.app.api.controller.option.CommandOption;

import java.util.Set;

public class SortOption implements CommandOption {
    private static final Set<String> VALID_SORT_OPTIONS = Set.of("name", "salary");

    private String sortBy;


    @Override
    public void process(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Опция --sort требует аргумент (name/salary).");
        }
        if (VALID_SORT_OPTIONS.contains(value)) {
            this.sortBy = value;
        } else {
            throw new IllegalArgumentException("Некорректное значение сортировки: " + value);
        }
    }

    public String getSortBy() {
        return sortBy;
    }
}
