package com.lseg.test.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Builder
@Data
public class LogEntry {

    private LocalTime timestamp;
    private String description;
    private LogType type;
    private String pid;

}
