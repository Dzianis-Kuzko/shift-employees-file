package com.korona.app.view;

import com.korona.app.api.dto.EmployeeDTO;
import com.korona.app.api.dto.StatisticsDTO;
import com.korona.app.view.writer.OutputWriter;
import com.korona.app.view.writer.impl.ConsoleWriter;

public class View {
    private OutputWriter writer;

    public View() {
        this.writer = new ConsoleWriter();
    }

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

    public void printStatistics(StatisticsDTO statisticsDTO){
        writer.writeLine(formatStatistics(statisticsDTO));
    }

    public void close() {
        writer.close();
    }

    private String formatEmployee(EmployeeDTO employee) {
        return String.format("%s,%d,%s,%.2f",
                employee.getPosition().getValue(),
                employee.getId(),
                employee.getName(),
                employee.getSalary());
    }

    private String formatStatistics(StatisticsDTO statisticsDTO){
        return String.format("%d,%.2f",
                statisticsDTO.getEmployeeCount(),
                statisticsDTO.getAverageSalary());
    }
}
