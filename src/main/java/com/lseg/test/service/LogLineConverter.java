package com.lseg.test.service;

import com.lseg.test.exception.InvalidLogLineException;
import com.lseg.test.model.LogEntry;
import com.lseg.test.model.LogType;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class LogLineConverter {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * Reads line by line and a produce an LogEntry object that contains the meta information
     * The format of the line: [TIMESTAMP],[DESCRIPTION],[TYPE],[PID]
     * where TYPE can be either START or END (LogType)
     * @param line
     * @return
     */
    public LogEntry readLine(String line) {
        String[] split = line.split(",");
        if (split.length != 4) {
            throw new InvalidLogLineException(String.format("Line %s has incorrect format", line));
        }

        return LogEntry.builder()
                .timestamp(LocalTime.parse(split[0].trim(), FORMATTER))
                .description(split[1].trim())
                .type(LogType.valueOf(split[2].trim()))
                .pid(split[3].trim())
                .build();
    }

}
