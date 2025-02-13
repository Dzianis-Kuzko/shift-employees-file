package com.korona.app.core.parser;

import com.korona.app.core.CommandLineConfig;
import com.korona.app.core.validator.CommandLineParamValidator;

public final class CommandLineParser {
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final int EXPECTED_PARTS_LENGTH = 2;

    private static final String SEPARATOR = "=";
    private static final String KEY_PREFIX = "--";
    private static final String KEY_SHORT_PREFIX = "-";

    private static final String SORT_OPTION = "--sort";
    private static final String SORT_SHORT_OPTION = "-s";
    private static final String ORDER_OPTION = "--order";
    private static final String ORDER_SHORT_OPTION = "-o";
    private static final String OUTPUT_OPTION = "--output";
    private static final String SHORT_OUTPUT_OPTION = "-o";
    private static final String NEW_SHORT_OUTPUT_OPTION = "-out";
    private static final String PATH_OPTION = "--path";
    private static final String SHORT_OUTPUT_PARAM_FILE = "-o=file";
    private static final String SHORT_OUTPUT_PARAM_CONSOLE = "-o=console";

    private static final String OUTPUT_CONSOLE_OPTIONS = "console";

    private final CommandLineConfig commandLineConfig;
    private final CommandLineParamValidator commandLineParamValidator;

    public CommandLineParser(CommandLineConfig commandLineConfig, CommandLineParamValidator commandLineParamValidator) {
        this.commandLineConfig = commandLineConfig;
        this.commandLineParamValidator = commandLineParamValidator;
    }

    public CommandLineConfig getCommandLineConfig() {
        return commandLineConfig;
    }

    public void parse(String[] args) {
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];

            if (arg.equals(SHORT_OUTPUT_PARAM_FILE) || arg.equals(SHORT_OUTPUT_PARAM_CONSOLE)) {
                arg = arg.replace(SHORT_OUTPUT_OPTION, NEW_SHORT_OUTPUT_OPTION);
            }

            if (arg.startsWith(KEY_PREFIX) || arg.startsWith(KEY_SHORT_PREFIX)) {
                i = processArg(arg, i, args);
            } else {
                throw new IllegalArgumentException("Некорректный формат аргумента: " + arg);
            }

        }
    }

    private int processArg(String arg, int index, String[] args) {
        String[] parts = arg.split(SEPARATOR, EXPECTED_PARTS_LENGTH);
        String key = null;
        String value = null;

        if (parts.length == EXPECTED_PARTS_LENGTH) {
            key = parts[KEY_INDEX];
            value = parts[VALUE_INDEX];
        } else {
            throw new IllegalArgumentException("Некорректный формат аргумента: " + arg);
        }

        switch (key) {
            case SORT_OPTION:
            case SORT_SHORT_OPTION:
                processSortOption(value);
                break;
            case ORDER_OPTION:
            case ORDER_SHORT_OPTION:
                processOrderOption(value);
                break;
            case OUTPUT_OPTION:
            case NEW_SHORT_OUTPUT_OPTION:
                processOutputOption(value);
                if (commandLineConfig.getOutput().equals(OUTPUT_CONSOLE_OPTIONS)) {
                    break;
                } else if (index + 1 < args.length && args[index + 1].startsWith(PATH_OPTION)) {
                    processFilePathOption(index, args);
                    index++;
                } else {
                    throw new IllegalArgumentException("Некорректный формат аргумента после опции " + value);
                }
                break;

            default:
                throw new IllegalArgumentException("Неизвестная опция: " + key);
        }
        return index;
    }

    private void processSortOption(String value) {
        if (commandLineParamValidator.isValidSortParam(value)) {
            commandLineConfig.setSortField(value);
        } else {
            throw new IllegalArgumentException("Ошибка.Некорректное значение сортировки: " + value);
        }
    }

    private void processOrderOption(String value) {
        if (commandLineParamValidator.isValidOrderParam(value)) {
            commandLineConfig.setOrder(value);
        } else {
            throw new IllegalArgumentException("Ошибка.Некорректное значение порядка сортировки: " + value);
        }
    }

    private void processOutputOption(String value) {
        if (commandLineParamValidator.isValidOutputParam(value)) {
            commandLineConfig.setOutput(value);
        } else {
            throw new IllegalArgumentException("Ошибка.Некорректное значение параметра вывода: " + value);
        }
    }

    private void processFilePathOption(int index, String[] args) {
        String[] nextParts = args[index + 1].split(SEPARATOR, EXPECTED_PARTS_LENGTH);
        if (nextParts.length == EXPECTED_PARTS_LENGTH) {
            commandLineConfig.setFilePath(nextParts[VALUE_INDEX]);
        } else {
            throw new IllegalArgumentException("Ошибка.Отсутствует путь к файлу после опции " + args[index]);
        }
    }

}

