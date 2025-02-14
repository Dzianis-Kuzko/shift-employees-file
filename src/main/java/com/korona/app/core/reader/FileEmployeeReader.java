package com.korona.app.core.reader;

import com.korona.app.core.exception.FileReadException;
import com.korona.app.core.parser.EmployeeDataParser;
import com.korona.app.core.service.EmployeeDataContainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileEmployeeReader {
    private static final Path FILE_PATH = Paths.get("data/employees.txt");

    private final EmployeeDataParser employeeDataParser;

    public FileEmployeeReader(EmployeeDataParser employeeDataParser) {
        this.employeeDataParser = employeeDataParser;
    }

    public void readDataFromFile(EmployeeDataContainer employeeDataContainer)  {

        try (BufferedReader br = Files.newBufferedReader(FILE_PATH)) {
            String line;
            while ((line = br.readLine()) != null) {
                employeeDataParser.parseLine(line, employeeDataContainer);
            }
        } catch (IOException e) {
            throw new FileReadException("Ошибка при чтении файла: " + FILE_PATH, e);
        }
    }

}
