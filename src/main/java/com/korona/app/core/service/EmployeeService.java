package com.korona.app.core.service;

import com.korona.app.api.dto.EmployeeDTO;
import com.korona.app.core.entity.Employee;
import com.korona.app.core.mapper.EmployeeMapper;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EmployeeService {

    private final EmployeeDataContainer employeeDataContainer;

    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeDataContainer employeeDataContainer, EmployeeMapper employeeMapper) {
        this.employeeDataContainer = employeeDataContainer;
        this.employeeMapper = employeeMapper;
    }

    public List<String> getSortedDepartments() {
        return employeeDataContainer.getManagers().stream()
                .map(Employee::getDepartment)
                .distinct()
                .sorted()
                .toList();
    }

    public List<EmployeeDTO> getManagersByDepartment(String department) {
        return employeeDataContainer.getManagers().stream()
                .filter(emp -> emp.getDepartment().equals(department))
                .map(employeeMapper::toEmployeeDTO)
                .toList();
    }

    public List<EmployeeDTO> getSortedEmployeesByManagerIds(String sortBy, String order, List<Integer> managerIds) {
        if (sortBy == null && order != null) {
            throw new IllegalArgumentException("Ошибка: Порядок сортировки задан без указания типа сортировки.");
        }

        Comparator<Employee> comparator;

        if ("name".equals(sortBy)) {
            comparator = Comparator.comparing(Employee::getName);
        } else if ("salary".equals(sortBy)) {
            comparator = Comparator.comparing(Employee::getSalary);
        } else if (sortBy == null) {
            return managerIds.stream()
                    .flatMap(managerId -> employeeDataContainer.getEmployeesByManagerId()
                            .getOrDefault(managerId, List.of()).stream())
                    .map(employeeMapper::toEmployeeDTO)
                    .toList();
        } else {
            throw new IllegalArgumentException("Ошибка: Некорректный параметр сортировки. Доступные: name, salary.");
        }

        if ("desc".equals(order)) {
            comparator = comparator.reversed();
        } else if (order != null && !"asc".equals(order)) {
            throw new IllegalArgumentException("Ошибка: Некорректный порядок сортировки. Доступные: asc, desc.");
        }

        return managerIds.stream()
                .flatMap(managerId -> employeeDataContainer.getEmployeesByManagerId()
                        .getOrDefault(managerId, List.of()).stream())
                .sorted(comparator)
                .map(employeeMapper::toEmployeeDTO)
                .toList();
    }

    public List<EmployeeDTO> getEmployeesWithoutManager() {
        Set<Integer> managerIds = employeeDataContainer.getManagers().stream()
                .map(Employee::getId)
                .collect(Collectors.toSet());

        return employeeDataContainer.getEmployeesByManagerId().entrySet().stream()
                .filter(entry -> !managerIds.contains(entry.getKey()))
                .flatMap(entry -> entry.getValue().stream())
                .map(employeeMapper::toEmployeeDTO)
                .toList();
    }

    public List<String> getInvalidData() {
        return employeeDataContainer.getIncorrectData();
    }
}
