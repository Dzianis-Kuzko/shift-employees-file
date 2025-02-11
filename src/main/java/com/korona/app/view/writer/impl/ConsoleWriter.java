package com.korona.app.view.writer.impl;

import com.korona.app.view.writer.OutputWriter;

public class ConsoleWriter implements OutputWriter {
    @Override
    public void writeLine(String line) {
        System.out.println(line);
    }

    @Override
    public void close() {

    }
}
