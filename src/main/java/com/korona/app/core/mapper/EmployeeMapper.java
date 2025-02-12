package com.korona.app.core.mapper;

import com.korona.app.api.dto.EmployeeDTO;
import com.korona.app.core.entity.Employee;

public class EmployeeMapper {
    public EmployeeDTO toEmployeeDTO(Employee employee) {
        if (employee == null) {
            return null;
        }

        return EmployeeDTO.builder()
                .id(employee.getId())
                .position(employee.getPosition())
                .name(employee.getName())
                .salary(employee.getSalary())
                .build();
    }
}
