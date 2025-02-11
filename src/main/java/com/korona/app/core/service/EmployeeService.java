package com.korona.app.core.service;

import com.korona.app.core.entity.Employee;
import com.korona.app.core.reader.FileEmployeeReader;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class EmployeeService {

    private final FileEmployeeReader fileEmployeeReader;

    private final List<Employee> employees;

    public EmployeeService(FileEmployeeReader fileEmployeeReader) {
        this.fileEmployeeReader = fileEmployeeReader;
        this.employees = fileEmployeeReader.readEmployeesFromFile();
    }

    public List<String> getSortedDepartments() {
        return employees.stream()
                .filter(emp -> emp.getPosition() == Employee.Position.MANAGER)
                .map(Employee::getDepartment)
                .sorted()
                .toList();
    }

    public Employee getManagerByDepartment(String department) {
        return employees.stream()
                .filter(emp -> emp.getPosition() == Employee.Position.MANAGER)
                .filter(emp -> emp.getDepartment().equals(department))
                .findFirst()
                .orElseThrow();
    }

    public Map<String, Employee> getManagersByDepartment() {
        return employees.stream()
                .filter(emp -> emp.getPosition() == Employee.Position.MANAGER)
                .collect(Collectors.toMap(Employee::getDepartment, emp -> emp, (e1, e2) -> e1));
    }

    public List<Employee> getSortedEmployeesByManagerId(String sortBy, String order, int managerId) {
        if (sortBy == null && order != null) {
            throw new IllegalArgumentException("Ошибка: Порядок сортировки задан без указания типа сортировки.");
        }

        Comparator<Employee> comparator;

        if ("name".equals(sortBy)) {
            comparator = Comparator.comparing(Employee::getName);
        } else if ("salary".equals(sortBy)) {
            comparator = Comparator.comparing(Employee::getSalary);
        } else if (sortBy == null) {
            return getEmployeesByManagerId(managerId);
        } else {
            throw new IllegalArgumentException("Ошибка: Некорректный параметр сортировки. Доступные: name, salary.");
        }

        if ("desc".equals(order)) {
            comparator = comparator.reversed();
        } else if (order != null && !"asc".equals(order)) {
            throw new IllegalArgumentException("Ошибка: Некорректный порядок сортировки. Доступные: asc, desc.");
        }

        return employees.stream()
                .filter(emp -> emp.getPosition() == Employee.Position.EMPLOYEE)
                .filter(emp -> emp.getManagerId() == managerId)
                .sorted(comparator)
                .toList();
    }

    public List<Employee> getEmployeesWithoutManager() {
        Set<Integer> managerIds = employees.stream()
                .filter(emp -> emp.getPosition() == Employee.Position.MANAGER)
                .map(Employee::getId)
                .collect(Collectors.toSet());

        return employees.stream()
                .filter(emp -> emp.getPosition() == Employee.Position.EMPLOYEE)
                .filter(emp -> !managerIds.contains(emp.getManagerId()))
                .toList();
    }

    private List<Employee> getEmployeesByManagerId(int managerId) {
        return employees.stream()
                .filter(emp -> emp.getPosition() == Employee.Position.EMPLOYEE)
                .filter(emp -> emp.getManagerId() == managerId)
                .toList();
    }

}
