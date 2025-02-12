package com.korona.app.api.controller.option.impl;

import com.korona.app.api.controller.option.CommandOption;

import java.util.Set;

public class OutputOption implements CommandOption {
    private static final Set<String> VALID_OUTPUT_OPTIONS = Set.of("console", "file");
    private String output = "console";
    private String path = null;

    @Override
    public void process(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Опция --output требует аргумент (console/file).");
        }

        if ("console".equals(value)) {
            this.output = "console";
            this.path = null;
        } else if ("file".equals(value)) {
            this.output = "file";
        } else {
            throw new IllegalArgumentException("Некорректное значение --output: " + value);
        }
    }

    public void setPath(String path) {
        if (!"file".equals(output)) {
            throw new IllegalArgumentException("Опция --path допустима только при --output=file.");
        }
        if (path == null || path.isBlank()) {
            throw new IllegalArgumentException("Не указан путь к файлу.");
        }
        this.path = path;
    }

    public String getOutput() {
        return output;
    }

    public String getPath() {
        return path;
    }

    public void validate() {
        if ("file".equals(output) && path == null) {
            throw new IllegalArgumentException("Указан --output=file, но отсутствует --path=<путь к файлу>.");
        }
    }

}
