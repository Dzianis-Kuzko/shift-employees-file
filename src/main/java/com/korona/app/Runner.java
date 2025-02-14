package com.korona.app;

import com.korona.app.api.controller.ConsoleController;
import com.korona.app.core.config.CommandLineConfig;
import com.korona.app.core.mapper.EmployeeMapper;
import com.korona.app.core.parser.CommandLineParser;
import com.korona.app.core.parser.EmployeeDataParser;
import com.korona.app.core.reader.FileEmployeeReader;
import com.korona.app.core.service.EmployeeDataContainer;
import com.korona.app.core.service.EmployeeService;
import com.korona.app.core.service.StatisticsService;
import com.korona.app.core.validator.CommandLineConfigValidator;
import com.korona.app.core.validator.CommandLineParamValidator;
import com.korona.app.core.validator.EmployeeDataValidator;
import com.korona.app.view.View;

public class Runner {
    public static void main(String[] args) {
        CommandLineConfig commandLineConfig = new CommandLineConfig();
        CommandLineParamValidator commandLineParamValidator = new CommandLineParamValidator();
        CommandLineParser commandLineParser = new CommandLineParser(commandLineConfig, commandLineParamValidator);
        CommandLineConfigValidator commandLineConfigValidator = new CommandLineConfigValidator();

        EmployeeMapper employeeMapper = new EmployeeMapper();
        EmployeeDataContainer employeeDataContainer = new EmployeeDataContainer();
        EmployeeDataValidator employeeDataValidator = new EmployeeDataValidator();
        EmployeeDataParser employeeDataParser = new EmployeeDataParser(employeeDataValidator);
        EmployeeService employeeService = new EmployeeService(employeeDataContainer, employeeMapper);

        FileEmployeeReader fileEmployeeReader = new FileEmployeeReader(employeeDataParser);

        View view = new View();

        StatisticsService statisticsService = new StatisticsService();

        ConsoleController consoleController = new ConsoleController(
                commandLineParser,
                employeeService,
                view,
                commandLineConfigValidator,
                statisticsService,
                fileEmployeeReader,
                employeeDataContainer
        );
        consoleController.execute(args);


    }
}
