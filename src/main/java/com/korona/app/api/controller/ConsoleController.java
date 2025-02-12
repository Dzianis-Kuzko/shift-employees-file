package com.korona.app.api.controller;

import com.korona.app.core.CommandLineConfig;
import com.korona.app.core.parser.CommandLineParser;

public class ConsoleController {
    private final CommandLineParser commandLineParser;

    public ConsoleController(CommandLineParser commandLineParser) {
        this.commandLineParser = commandLineParser;
    }

    public void execute(String[] args) {
        commandLineParser.parse(args);

        CommandLineConfig commandLineConfig = commandLineParser.getConfig();

        System.out.println(commandLineConfig);
    }


}
