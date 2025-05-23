package com.lseg.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class LogService {

    private final LogLineConverter converter;
    private final LogEntryProcessor processor;

    public LogService(@Autowired LogLineConverter converter,
                      @Autowired LogEntryProcessor processor) {
        this.converter = converter;
        this.processor = processor;
    }

    public void process(String inputFile) {
        try {
            Path path = Paths.get(inputFile);
            Files.lines(path)
                    .map(converter::readLine)
                    .forEach(processor::process);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
