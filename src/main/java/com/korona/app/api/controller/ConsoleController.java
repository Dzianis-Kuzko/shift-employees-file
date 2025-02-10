package com.korona.app.controller;

import com.korona.app.core.ArgumentsProcessor;

public class ConsoleController {
    private final ArgumentsProcessor argumentsProcessor;

    public ConsoleController(ArgumentsProcessor argumentsProcessor) {
        this.argumentsProcessor = argumentsProcessor;
    }

    public void execute(String[] args){
        argumentsProcessor.processArguments(args);
    }


}
