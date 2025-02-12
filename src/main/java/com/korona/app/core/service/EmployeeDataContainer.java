package com.korona.app.core.service;

import com.korona.app.core.entity.Employee;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class EmployeeDataContainer {

    private final Map<Integer, List<Employee>> employeesByManagerId;

    private final List<Employee> managers;

    private final List<String> incorrectData;

    public EmployeeDataContainer() {
        this.employeesByManagerId = new HashMap<>();
        this.managers = new ArrayList<>();
        this.incorrectData = new ArrayList<>();
    }
}
