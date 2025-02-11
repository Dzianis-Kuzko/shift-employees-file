package com.korona.app.core.reader;

import com.korona.app.core.entity.Employee;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileEmployeeReader {
    private static final Path FILE_PATH = Paths.get("data/employees.txt");

    public List<Employee> readEmployeesFromFile() {
        List<Employee> employees = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(FILE_PATH)) {
            String line;
            while ((line = br.readLine()) != null) {
                employees.add(parseLineToEmployee(line));
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
        return employees;
    }

    private Employee parseLineToEmployee(String line) {
        String[] parts = line.split(",");

        if (parts.length < 5) {
            throw new IllegalArgumentException("Некорректный формат строки: " + line);
        }

        String type = parts[0].trim();
        int id = Integer.parseInt(parts[1].trim());
        String name = parts[2].trim();
        BigDecimal salary = new BigDecimal(parts[3].trim());

        if (parts[0].trim().equals(Employee.Position.EMPLOYEE.getValue())) {
            int managerId = Integer.parseInt(parts[4].trim());

            return Employee.builder()
                    .id(id)
                    .position(Employee.Position.EMPLOYEE)
                    .name(name)
                    .salary(salary)
                    .managerId(managerId)
                    .build();

        } else if (parts[0].trim().equals(Employee.Position.MANAGER.getValue())) {
            String department = parts[4].trim();

            return Employee.builder()
                    .id(id)
                    .position(Employee.Position.MANAGER)
                    .name(name)
                    .salary(salary)
                    .department(department)
                    .build();
        } else {
            throw new IllegalArgumentException("Некорректный формат строки: " + line);
        }


    }
}
