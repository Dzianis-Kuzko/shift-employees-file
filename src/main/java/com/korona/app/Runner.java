package com.korona.app;

import com.korona.app.api.controller.ConsoleController;
import com.korona.app.core.parser.CommandLineParser;
import com.korona.app.core.mapper.EmployeeMapper;
import com.korona.app.core.parser.EmployeeDataParser;
import com.korona.app.core.reader.FileEmployeeReader;
import com.korona.app.core.service.EmployeeDataContainer;
import com.korona.app.core.service.EmployeeService;
import com.korona.app.core.validator.EmployeeDataValidator;

public class Runner {
    public static void main(String[] args) {
        CommandLineParser commandLineParser = new CommandLineParser();

        EmployeeDataContainer employeeDataContainer = new EmployeeDataContainer();
        EmployeeDataValidator employeeDataValidator = new EmployeeDataValidator();
        EmployeeDataParser employeeDataParser = new EmployeeDataParser(employeeDataValidator);
        FileEmployeeReader fileEmployeeReader = new FileEmployeeReader(employeeDataParser);

        EmployeeMapper employeeMapper = new EmployeeMapper();
        EmployeeService employeeService = new EmployeeService(employeeDataContainer, employeeMapper);

        fileEmployeeReader.readDataFromFile(employeeDataContainer);


        ConsoleController consoleController = new ConsoleController(commandLineParser);

        consoleController.execute(args);



    }
}
