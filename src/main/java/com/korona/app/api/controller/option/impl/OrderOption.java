package com.korona.app.api.controller.option.impl;

import com.korona.app.api.controller.option.CommandOption;

import java.util.Set;

public class OrderOption implements CommandOption {
    private static final Set<String> VALID_ORDER_OPTIONS = Set.of("asc", "desc");

    private String order;

    @Override
    public void process(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Опция --order требует аргумент (asc/desc).");
        }
        if (VALID_ORDER_OPTIONS.contains(value)) {
            this.order = value;
        } else {
            throw new IllegalArgumentException("Некорректный порядок сортировки: " + value);
        }
    }

    public String getOrder() {
        return order;
    }
}
