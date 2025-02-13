package com.korona.app.view;

import com.korona.app.api.dto.EmployeeDTO;
import com.korona.app.view.writer.OutputWriter;

public class View {
    private OutputWriter writer;

    public void setWriter(OutputWriter writer) {
        this.writer = writer;
    }

    public void printMessage(String message) {
        writer.writeLine(message);
    }

    public void printDepartment(String department) {
        writer.writeLine(department);
    }

    public void printManager(EmployeeDTO manager) {
        writer.writeLine(formatEmployee(manager));
    }

    public void printEmployee(EmployeeDTO employee) {
        writer.writeLine(formatEmployee(employee));
    }

    public void printInvalidData(String invalidData) {
        writer.writeLine(invalidData);
    }

    public void close() {
        writer.close();
    }

    private String formatEmployee(EmployeeDTO employee) {
        return String.format("%s,%d,%s,%.2f",
                employee.getPosition(),
                employee.getId(),
                employee.getName(),
                employee.getSalary().doubleValue());
    }
}
