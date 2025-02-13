package com.korona.app.api.controller;

import com.korona.app.api.dto.EmployeeDTO;
import com.korona.app.core.CommandLineConfig;
import com.korona.app.core.parser.CommandLineParser;
import com.korona.app.core.service.EmployeeService;
import com.korona.app.core.service.StatisticsService;
import com.korona.app.core.validator.CommandLineConfigValidator;
import com.korona.app.view.View;
import com.korona.app.view.writer.impl.ConsoleWriter;
import com.korona.app.view.writer.impl.FileWriter;
import lombok.AllArgsConstructor;

import java.nio.file.Path;
import java.util.List;

@AllArgsConstructor
public class ConsoleController {

    private static final String INVALID_DATA_HEADER = "Некорректные данные:";

    private final CommandLineParser commandLineParser;
    private final EmployeeService employeeService;
    private final View view;
    private final CommandLineConfigValidator commandLineConfigValidator;
    private final StatisticsService statisticsService;

    public void execute(String[] args) {
        try {
            commandLineParser.parse(args);

            CommandLineConfig commandLineConfig = commandLineParser.getCommandLineConfig();

            commandLineConfigValidator.validate(commandLineConfig);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return;
        }


        CommandLineConfig commandLineConfig = commandLineParser.getCommandLineConfig();

        setWriter(commandLineConfig);

        render(commandLineConfig);

        view.close();

        System.out.println("------------------" + commandLineConfig);////////////---------
    }

    private void render(CommandLineConfig commandLineConfig) {
        List<String> sortedDepartments = employeeService.getSortedDepartments();

        for (String department : sortedDepartments) {

            renderDepartment(department);

            List<EmployeeDTO> managersByDepartment = employeeService.getManagersByDepartment(department);

            renderManagersByDepartment(managersByDepartment);

            renderEmployeesByManagers(managersByDepartment, commandLineConfig);
        }

        renderInvalidData();
    }

    private void renderDepartment(String department) {
        view.printDepartment(department);
    }

    private void renderManagersByDepartment(List<EmployeeDTO> managersByDepartment) {
        managersByDepartment.forEach(manager -> view.printManager(manager));
    }

    private void renderEmployeesByManagers(List<EmployeeDTO> managersByDepartment, CommandLineConfig commandLineConfig) {
        List<Integer> managerIds = managersByDepartment.stream()
                .map(EmployeeDTO::getId)
                .toList();

        List<EmployeeDTO> sortedEmployeesByManagerIds = employeeService.getSortedEmployeesByManagerIds(
                commandLineConfig.getSortField(),
                commandLineConfig.getOrder(),
                managerIds
        );

        sortedEmployeesByManagerIds.forEach(view::printEmployee);
    }

    private void renderInvalidData() {
        view.printMessage(INVALID_DATA_HEADER);
        employeeService.getInvalidData().forEach(view::printInvalidData);
        employeeService.getEmployeesWithoutManager().forEach(view::printEmployee);
    }

    private void setWriter(CommandLineConfig commandLineConfig) {
        if (commandLineConfig.getFilePath() != null) {
            FileWriter fileWriter = new FileWriter(Path.of(commandLineConfig.getFilePath()));
            view.setWriter(fileWriter);
        } else {
            view.setWriter(new ConsoleWriter());
        }
    }


}
