package com.lseg.test.service;

import com.lseg.test.model.LogEntry;
import com.lseg.test.model.ResultType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@Slf4j
public class LogResultReporter {

    public void processResult(LogEntry logEntry, ResultType type, long diff) {
        processResult(type.equals(ResultType.WARN) ? log::warn : log::error, logEntry, diff);
    }

    private void processResult(Consumer<String> consumer, LogEntry logEntry, long diff) {
        consumer.accept(String.format("[%s][%s] Processed exceeded 10 min. Actual process time: [%s]s",
                logEntry.getPid(),
                logEntry.getDescription(),
                diff));
    }

}
