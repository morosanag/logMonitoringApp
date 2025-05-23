package com.lseg.test.service;

import com.lseg.test.exception.InvalidLogLineException;
import com.lseg.test.model.LogEntry;
import com.lseg.test.model.LogType;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class LogEntryProcessorTest {

    @Test
    public void testNoStartTime() {
        // Given
        LogEntryProcessor processor = new LogEntryProcessor(new LogResultReporter());
        String expectedExceptionMessage = "Log line with pidId 1 does not have a start time";
        Exception actualException = null;
        LogEntry logEntry = LogEntry.builder()
                .pid("1")
                .timestamp(LocalTime.now())
                .description("desc")
                .type(LogType.END)
                .build();

        // When
        try {
            processor.process(logEntry);
        } catch (Exception exception) {
            actualException = exception;
        }

        // Then
        assertNotNull(actualException);
        assertTrue(actualException instanceof InvalidLogLineException);
        assertEquals(expectedExceptionMessage, actualException.getMessage());
    }

}
