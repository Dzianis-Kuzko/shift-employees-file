package com.korona.app.core.service;

import com.korona.app.api.dto.EmployeeDTO;
import com.korona.app.api.dto.StatisticsDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class StatisticsService {
    public static final int DECIMAL_PLACES = 2;

    public StatisticsDTO calculateStatistics(List<EmployeeDTO> managers, List<EmployeeDTO> employees) {

        int managerCount = managers.size();
        BigDecimal managerTotalSalary = managers.stream()
                .map(EmployeeDTO::getSalary)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int employeeCount = employees.size();
        BigDecimal employeeTotalSalary = employees.stream()
                .map(EmployeeDTO::getSalary)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int totalCount = managerCount + employeeCount;
        BigDecimal totalSalary = managerTotalSalary.add(employeeTotalSalary);

        BigDecimal averageSalary = totalCount > 0
                ? totalSalary.divide(BigDecimal.valueOf(totalCount), DECIMAL_PLACES, RoundingMode.CEILING)
                : BigDecimal.ZERO;

        return new StatisticsDTO(totalCount, averageSalary);
    }
}
