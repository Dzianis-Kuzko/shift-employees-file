package com.korona.app.core.validator;

import com.korona.app.core.Position;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class EmployeeDataValidator {
    private Set<Integer> existingIds;

    public EmployeeDataValidator() {
        this.existingIds = new HashSet<>();
    }

    public boolean isValidPosition(String position) {
        return position.equals(Position.EMPLOYEE.getValue()) ||
                position.equals(Position.MANAGER.getValue());
    }

    public boolean isValidId(int id) {
        if (existingIds.contains(id)) {
            return false;
        }

        existingIds.add(id);

        return id >= 0;
    }

    public boolean isValidSalary(BigDecimal salary) {
        return salary.compareTo(BigDecimal.ZERO) > 0 && salary.scale() <= 2;
    }

    public boolean isValidManagerId(int managerId) {
        return managerId >= 0;
    }
}
