package com.korona.app.api.controller;

import com.korona.app.api.dto.EmployeeDTO;
import com.korona.app.api.dto.StatisticsDTO;
import com.korona.app.core.config.CommandLineConfig;
import com.korona.app.core.exception.FileCloseException;
import com.korona.app.core.exception.FileCreationException;
import com.korona.app.core.exception.FileReadException;
import com.korona.app.core.parser.CommandLineParser;
import com.korona.app.core.reader.FileEmployeeReader;
import com.korona.app.core.service.EmployeeDataContainer;
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
    private final FileEmployeeReader fileEmployeeReader;
    private final EmployeeDataContainer employeeDataContainer;

    public void execute(String[] args) {
        CommandLineConfig commandLineConfig = null;

        try {
            commandLineParser.parse(args);

            commandLineConfig = commandLineParser.getCommandLineConfig();

            commandLineConfigValidator.validate(commandLineConfig);
        } catch (IllegalArgumentException e) {
            view.printMessage(e.getMessage());
            System.exit(0);
        }

        loadData();

        try {
            setWriter(commandLineConfig);

            render(commandLineConfig);

            view.close();

        } catch (FileCreationException | FileReadException | FileCloseException e) {
            view.printMessage(e.getMessage());
            System.exit(0);
        }

    }

    private void loadData() {
        try {
            fileEmployeeReader.readDataFromFile(employeeDataContainer);
        } catch (FileReadException e) {
            view.printMessage(e.getMessage());
            System.exit(0);
        }
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

        List<EmployeeDTO> sortedEmployeesByManagerIds = employeeService.getEmployeesFilteredByManagerIdsAndSorted(
                commandLineConfig.getSortField(),
                commandLineConfig.getOrder(),
                managerIds
        );

        sortedEmployeesByManagerIds.forEach(view::printEmployee);

        renderStatistics(managersByDepartment, sortedEmployeesByManagerIds);

    }

    private void renderStatistics(List<EmployeeDTO> managers, List<EmployeeDTO> employees) {
        StatisticsDTO statisticsDTO = statisticsService.calculateStatistics(managers, employees);

        view.printStatistics(statisticsDTO);
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
