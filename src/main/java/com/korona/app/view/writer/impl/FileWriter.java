package com.korona.app.view.writer.impl;

import com.korona.app.core.exception.FileCloseException;
import com.korona.app.core.exception.FileCreationException;
import com.korona.app.core.exception.FileWriteException;
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
            throw new FileCreationException("Ошибка при создании файла: " + e.getMessage(), e);
        }
    }

    @Override
    public void writeLine(String line) {
        try {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            throw new FileWriteException("Ошибка при записи в файл: " + e.getMessage(), e);
        }
    }

    @Override
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            throw new FileCloseException("Ошибка при закрытии файла.", e);
        }
    }
}
