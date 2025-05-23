package com.lseg.test.service;

import com.lseg.test.model.LogEntry;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;

import static com.lseg.test.model.ResultType.ERROR;
import static com.lseg.test.model.ResultType.WARN;
import static org.apache.commons.collections4.CollectionUtils.isEqualCollection;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class LogServiceTest {

    @Test
    public void testLogReportCall() {
        // Given
        List<String> exceptedWarnPids = Arrays.asList("71766", "87228", "50295", "27222", "87570", "99672", "86716", "98746", "24482");
        List<String> exceptedErrorPids = Arrays.asList("39547", "45135", "81258", "22003", "85742", "39860", "52532", "62401", "23703", "70808");

        LogResultReporter logResultReporter = mock(LogResultReporter.class);
        LogEntryProcessor processor = new LogEntryProcessor(logResultReporter);
        LogService logService = new LogService(new LogLineConverter(), processor);

        ArgumentCaptor<LogEntry> warnLogEntryArgumentCaptor = ArgumentCaptor.forClass(LogEntry.class);
        ArgumentCaptor<LogEntry> errorLogEntryArgumentCaptor = ArgumentCaptor.forClass(LogEntry.class);

        // When
        logService.process("src/test/resources/logs.log");

        // Then
        verify(logResultReporter, times(9)).processResult(warnLogEntryArgumentCaptor.capture(), eq(WARN), anyLong());
        assertTrue(isEqualCollection(exceptedWarnPids, warnLogEntryArgumentCaptor.getAllValues().stream().map(LogEntry::getPid).toList()));
        verify(logResultReporter, times(10)).processResult(errorLogEntryArgumentCaptor.capture(), eq(ERROR), anyLong());
        assertTrue(isEqualCollection(exceptedErrorPids, errorLogEntryArgumentCaptor.getAllValues().stream().map(LogEntry::getPid).toList()));
    }

}
