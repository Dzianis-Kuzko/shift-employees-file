package com.korona.app;

import com.korona.app.core.ArgumentsProcessor;
import com.korona.app.api.controller.ConsoleController;

public class Runner {
    public static void main(String[] args) {
        ArgumentsProcessor argumentsProcessor = new ArgumentsProcessor();

        ConsoleController consoleController = new ConsoleController(argumentsProcessor);
        consoleController.execute(args);
    }
}
