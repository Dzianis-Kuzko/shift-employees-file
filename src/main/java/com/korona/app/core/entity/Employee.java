package com.korona.app.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Employee {

    private int id;

    private Position position;

    private String name;

    private BigDecimal salary;

    private Integer managerId;

    private String department;

    public enum Position {
        EMPLOYEE("Employee"),
        MANAGER("Manager");

        private final String value;

        Position(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}
