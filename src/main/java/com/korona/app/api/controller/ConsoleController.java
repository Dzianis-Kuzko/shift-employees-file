package com.korona.app.api.controller;

import com.korona.app.core.ArgumentsProcessor;

public class ConsoleController {
    private final ArgumentsProcessor argumentsProcessor;

    public ConsoleController(ArgumentsProcessor argumentsProcessor) {
        this.argumentsProcessor = argumentsProcessor;
    }

    public void execute(String[] args) {
        ArgumentsProcessor.process(args);
    }


}
