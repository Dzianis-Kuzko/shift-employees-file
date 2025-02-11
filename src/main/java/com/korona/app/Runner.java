package com.korona.app;

import com.korona.app.api.controller.ConsoleController;
import com.korona.app.core.ArgumentsProcessor;
import com.korona.app.core.CommandLineConfig;
import com.korona.app.core.entity.Employee;
import com.korona.app.core.reader.FileEmployeeReader;
import com.korona.app.core.service.EmployeeService;

import java.util.Map;

public class Runner {
    public static void main(String[] args) {
        ArgumentsProcessor argumentsProcessor = new ArgumentsProcessor();

        FileEmployeeReader fileEmployeeReader = new FileEmployeeReader();

        EmployeeService employeeService = new EmployeeService(fileEmployeeReader);

        ConsoleController consoleController = new ConsoleController(argumentsProcessor);
        consoleController.execute(args);


        Map<String, Employee> managerByDepartment = employeeService.getManagersByDepartment();
        System.out.println(employeeService.getSortedDepartments());
        System.out.println(employeeService.getManagerByDepartment("Dev"));


    }
}
