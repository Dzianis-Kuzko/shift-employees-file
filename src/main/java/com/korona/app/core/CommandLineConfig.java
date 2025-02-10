package com.korona.app.core;

public final class CommandLineConfig {
    private final String sortField;
    private final String order;
    private final String output;
    private final String filePath;

    public CommandLineConfig(String sortField, String order, String output, String filePath) {
        this.sortField = sortField;
        this.order = order;
        this.output = output;
        this.filePath = filePath;
    }

    public String getSortField() {
        return sortField;
    }

    public String getOrder() {
        return order;
    }

    public String getOutput() {
        return output;
    }

    public String getFilePath() {
        return filePath;
    }
}
