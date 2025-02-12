package com.korona.app.core.parser;

import com.korona.app.api.controller.option.CommandOption;
import com.korona.app.api.controller.option.impl.OrderOption;
import com.korona.app.api.controller.option.impl.OutputOption;
import com.korona.app.api.controller.option.impl.SortOption;
import com.korona.app.core.CommandLineConfig;
import com.korona.app.core.validator.CommandLineParamValidator;

import java.util.HashMap;
import java.util.Map;

public final class CommandLineParser {
    private static final String SEPARATOR = "=";
    private static final String KEY_PREFIX = "--";
    private static final String KEY_SHORT_PREFIX = "-";
    private static final String EMPTY_STRING = "";

    private static final String SORT_OPTION = "--sort";
    private static final String SORT_SHORT_OPTION = "-s";
    private static final String ORDER_OPTION = "--order";
    private static final String ORDER_SHORT_OPTION = "-o";
    private static final String OUTPUT_OPTION = "--output";
    private static final String SHORT_OUTPUT_OPTION = "-out";
    private static final String PATH_OPTION = "--path";
    private static final String SHORT_OUTPUT_PARAM_FILE = "-o=file";
    private static final String SHORT_OUTPUT_PARAM_CONSOLE = "-o=console";

    private final CommandLineConfig commandLineConfig;
    private final CommandLineParamValidator commandLineParamValidator;

    private final Map<String, CommandOption> options = new HashMap<>();
    private String pendingPathValue = null; // Для обработки --path

    public CommandLineParser(CommandLineConfig commandLineConfig, CommandLineParamValidator commandLineParamValidator) {
        this.commandLineConfig = commandLineConfig;
        this.commandLineParamValidator = commandLineParamValidator;
        options.put("--sort", new SortOption());
        options.put("-s", new SortOption());
        options.put("--order", new OrderOption());
        options.put("-o", new OrderOption());
        options.put("--output", new OutputOption());
        options.put("--path", null); // Специальный флаг без объекта
    }

    public void parse(String[] args) {
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];

            if (arg.equals(SHORT_OUTPUT_PARAM_FILE) || arg.equals(SHORT_OUTPUT_PARAM_CONSOLE)) {
            }

            if (arg.startsWith(KEY_PREFIX) || arg.startsWith(KEY_SHORT_PREFIX)) {

                String[] parts = arg.split(SEPARATOR, 2);
                String key = parts[0];
                String value = parts.length > 1 ? parts[1] : null;

                switch (key) {
                    case SORT_OPTION:
                    case SORT_SHORT_OPTION:
                        if (commandLineParamValidator.isValidSortParam(value)) {
                            commandLineConfig.setSortField(value);
                        }
                        break;
                    case ORDER_OPTION:
                    case ORDER_SHORT_OPTION:
                        if (commandLineParamValidator.isValidOrderParam(value)) {
                            commandLineConfig.setOrder(value);
                        }
                        break;
                    case OUTPUT_OPTION:
                    case SHORT_OUTPUT_OPTION:

                }


            } else {
                System.err.println("Некорректный формат аргумента: " + arg);
            }


        }

        OutputOption outputOption = (OutputOption) options.get("--output");

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];

            if (arg.startsWith("--") || arg.startsWith("-")) {
                String[] parts = arg.split("=", 2);
                String key = parts[0];
                String value = parts.length > 1 ? parts[1] : null;

                if ("--path".equals(key)) {
                    if (value == null && i + 1 < args.length) {
                        value = args[++i]; // Берем следующий аргумент как значение
                    }
                    pendingPathValue = value;
                } else {
                    CommandOption option = options.get(key);
                    if (option != null) {
                        try {
                            option.process(value);
                        } catch (IllegalArgumentException e) {
                            System.err.println("Ошибка: " + e.getMessage());
                        }
                    } else {
                        System.err.println("Неизвестная опция: " + key);
                    }
                }
            } else {
                System.err.println("Некорректный формат аргумента: " + arg);
            }
        }

        // Применяем --path, если он задан
        if (pendingPathValue != null) {
            try {
                outputOption.setPath(pendingPathValue);
            } catch (IllegalArgumentException e) {
                System.err.println("Ошибка: " + e.getMessage());
            }
        }

        // Проверяем корректность аргументов
        try {
            outputOption.validate();
        } catch (IllegalArgumentException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }

    public CommandLineConfig getConfig() {
        return new CommandLineConfig(
                ((SortOption) options.get("--sort")).getSortBy(),
                ((OrderOption) options.get("--order")).getOrder(),
                ((OutputOption) options.get("--output")).getOutput(),
                ((OutputOption) options.get("--output")).getPath()
        );
    }
}

