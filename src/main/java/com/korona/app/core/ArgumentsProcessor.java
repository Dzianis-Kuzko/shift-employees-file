package com.korona.app.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class ArgumentsProcessor {

    private static final Set<String> VALID_SORT_OPTIONS = Set.of("name", "salary");
    private static final Set<String> VALID_ORDER_OPTIONS = Set.of("asc", "desc");
    private static final Set<String> VALID_OUTPUT_OPTIONS = Set.of("console", "file");

    public static CommandLineConfig process(String[] args) {
        Map<String, String> options = parseArguments(args);
        validateArguments(options);

        String sortBy = parseSortOption(options);
        String order = parseOrderOption(options);
        String output = parseOutputOption(options);
        String filePath = options.get("path");

        return new CommandLineConfig(sortBy, order, output, filePath);
    }

    private static Map<String, String> parseArguments(String[] args) {
        Map<String, String> options = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            String[] parts = arg.split("=", 2);
            String key = parts[0].replace("--", "").replace("-", "");
            String value = (parts.length > 1) ? parts[1] : null;
            options.put(key, value);

            if(value.equals("file")){
                parts = args[i+1].split("=", 2);
                key = parts[0].replace("--", "").replace("-", "");
                if (!key.equals("path")){
                    throw new IllegalArgumentException("Ошибка: После --file !!!!!!");
                }
            }
        }
        return options;
    }

    private static String parseSortOption(Map<String, String> options) {
        String sortBy = options.get("sort");
        if (sortBy != null && !VALID_SORT_OPTIONS.contains(sortBy)) {
            throw new IllegalArgumentException("Ошибка: Неверное значение --sort (допустимо: name, salary)");
        }
        return sortBy;
    }

    private static String parseOrderOption(Map<String, String> options) {
        String order = options.get("order");
        if (order != null && !VALID_ORDER_OPTIONS.contains(order)) {
            throw new IllegalArgumentException("Ошибка: Неверное значение --order (допустимо: asc, desc)");
        }
        return order;
    }

    private static String parseOutputOption(Map<String, String> options) {
        String output = options.getOrDefault("output", "console");
        if (!VALID_OUTPUT_OPTIONS.contains(output)) {
            throw new IllegalArgumentException("Ошибка: Неверное значение --output (допустимо: console, file)");
        }
        return output;
    }

    private static void validateArguments(Map<String, String> options) {
        if (options.containsKey("order") && !options.containsKey("sort")) {
            throw new IllegalArgumentException("Ошибка: Нельзя указывать --order без --sort");
        }
        if (options.containsKey("path") && !"file".equals(options.get("output"))) {
            throw new IllegalArgumentException("Ошибка: --path можно указывать только с --output=file");
        }
    }
}

