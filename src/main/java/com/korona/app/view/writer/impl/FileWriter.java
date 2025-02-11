package com.korona.app.view.writer.impl;

import com.korona.app.view.writer.OutputWriter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriter implements OutputWriter {
    private final BufferedWriter writer;

    public FileWriter(Path path) {
        try {
            this.writer = Files.newBufferedWriter(path);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при создании файла: " + e.getMessage());
        }
    }

    @Override
    public void writeLine(String line) {
        try {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при записи в файл: " + e.getMessage());
        }
    }

    @Override
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            System.err.println("Ошибка при закрытии файла.");
        }
    }
}
