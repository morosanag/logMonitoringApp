package com.lseg.test.service;

import com.lseg.test.exception.InvalidLogLineException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogLineConverterTest {

    @Test
    public void testInvalidLogLine() {
        // Given
        LogLineConverter logLineConverter = new LogLineConverter();
        String logLine = "11:49:37,scheduled task 515, END";
        String exceptedExceptionMessage = String.format("Line %s has incorrect format", logLine);
        Exception actualException = null;

        // When
        try {
            logLineConverter.readLine(logLine);
        } catch (Exception exception) {
            actualException = exception;
        }

        // Then
        assertNotNull(actualException);
        assertTrue(actualException instanceof InvalidLogLineException);
        assertEquals(exceptedExceptionMessage, actualException.getMessage());
    }

}
