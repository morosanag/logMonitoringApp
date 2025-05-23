package com.lseg.test.service;

import com.lseg.test.exception.InvalidLogLineException;
import com.lseg.test.model.LogEntry;
import com.lseg.test.model.LogType;
import com.lseg.test.model.ResultType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class LogEntryProcessor {

    private final ConcurrentHashMap<String, LogEntry> logEntryMaps;
    private final LogResultReporter logResultReporter;

    public LogEntryProcessor(@Autowired LogResultReporter logResultReporter) {
        this.logResultReporter = logResultReporter;
        this.logEntryMaps = new ConcurrentHashMap<>();
    }

    /**
     * Log line produced has the following format: [[PID]][[DESCRIPTION]]Processed exceeded [5 or 10] min. Actual process time: [DIFF]
     *
     * @param logEntry
     */
    public void process(LogEntry logEntry) {
        if (logEntry.getType().equals(LogType.START)) {
            logEntryMaps.put(logEntry.getPid(), logEntry);
        } else {
            if (!logEntryMaps.containsKey(logEntry.getPid())) {
                throw new InvalidLogLineException(String.format("Log line with pidId %s does not have a start time", logEntry.getPid()));
            }
            // Remove the entry from cache so we don't reach the memory limit
            LogEntry startLonEntry = logEntryMaps.remove(logEntry.getPid());

            long diff = ChronoUnit.SECONDS.between(startLonEntry.getTimestamp(), logEntry.getTimestamp());
            if (diff > 600) {
                logResultReporter.processResult(logEntry, ResultType.ERROR, diff);
            } else if (diff > 300) {
                logResultReporter.processResult(logEntry, ResultType.WARN, diff);
            }
        }
    }


}
