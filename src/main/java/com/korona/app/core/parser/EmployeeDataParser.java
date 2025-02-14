package com.korona.app.core.parser;

import com.korona.app.core.entity.Employee;
import com.korona.app.core.enums.Position;
import com.korona.app.core.service.EmployeeDataContainer;
import com.korona.app.core.validator.EmployeeDataValidator;

import java.math.BigDecimal;
import java.util.ArrayList;

public class EmployeeDataParser {
    private static final String SEPARATOR = ",";
    private static final int POSITION_INDEX = 0;
    private static final int ID_INDEX = 1;
    private static final int NAME_INDEX = 2;
    private static final int SALARY_INDEX = 3;
    private static final int EXTRA_DATA_INDEX = 4;

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
            String position = parts[POSITION_INDEX].trim();
            int id = Integer.parseInt(parts[ID_INDEX].trim());
            String name = parts[NAME_INDEX].trim();
            BigDecimal salary = new BigDecimal(parts[SALARY_INDEX].trim());
            String extraData = parts[EXTRA_DATA_INDEX].trim();

            if (!isValidData(position, id, salary, employeeDataContainer, line)) {
                return;
            }

            processPositionCreateEntities(line, position, id, name, salary, extraData, employeeDataContainer);

        } catch (Exception e) {
            employeeDataContainer.getIncorrectData().add(line);
        }
    }

    private void processPositionCreateEntities(
            String line,
            String position,
            int id,
            String name,
            BigDecimal salary,
            String extraData,
            EmployeeDataContainer employeeDataContainer) {

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
