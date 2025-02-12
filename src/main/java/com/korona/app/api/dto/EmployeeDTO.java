package com.korona.app.api.dto;

import com.korona.app.core.Position;
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
@Builder
@ToString
public class EmployeeDTO {

    private int id;

    private Position position;

    private String name;

    private BigDecimal salary;

}
