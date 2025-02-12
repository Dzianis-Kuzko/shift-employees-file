package com.korona.app.core.parser;

import com.korona.app.core.Position;
import com.korona.app.core.entity.Employee;
import com.korona.app.core.service.EmployeeDataContainer;
import com.korona.app.core.validator.EmployeeDataValidator;

import java.math.BigDecimal;
import java.util.ArrayList;

public class EmployeeDataParser {
    private static final String SEPARATOR = ",";

    private final EmployeeDataValidator validator;

    public EmployeeDataParser(EmployeeDataValidator validator) {
        this.validator = validator;
    }

    public void parseLine(String line, EmployeeDataContainer employeeDataContainer) {
        String[] parts = line.split(SEPARATOR);

        if (parts.length != 5) {
            employeeDataContainer.getIncorrectData().add(line);
            return;
        }

        try {

            String position = parts[0].trim();
            int id = Integer.parseInt(parts[1].trim());
            String name = parts[2].trim();
            BigDecimal salary = new BigDecimal(parts[3].trim());
            String extraData = parts[4].trim();

            if (!isValidData(position, id, salary, employeeDataContainer, line)) {
                return;
            }

            if (position.equals(Position.EMPLOYEE.getValue())) {

                int managerId = Integer.parseInt(extraData);
                if (!validator.isValidManagerId(managerId)) {
                    employeeDataContainer.getIncorrectData().add(line);
                    return;
                }

                Employee employee = Employee.builder()
                        .id(id)
                        .position(Position.EMPLOYEE)
                        .name(name)
                        .salary(salary)
                        .managerId(managerId)
                        .build();

                employeeDataContainer.getEmployeesByManagerId()
                        .computeIfAbsent(managerId, k -> new ArrayList<>())
                        .add(employee);

            } else {
                employeeDataContainer.getManagers().add(
                        Employee.builder()
                                .id(id)
                                .position(Position.MANAGER)
                                .name(name)
                                .salary(salary)
                                .department(extraData)
                                .build()
                );
            }
        } catch (Exception e) {
            employeeDataContainer.getIncorrectData().add(line);
        }
    }


    private boolean isValidData(String position, int id, BigDecimal salary,
                                EmployeeDataContainer employeeDataContainer, String line) {

        if (!validator.isValidPosition(position) || !validator.isValidId(id) || !validator.isValidSalary(salary)) {
            employeeDataContainer.getIncorrectData().add(line);
            return false;
        }
        return true;
    }
}
