package com.lseg.test.exception;

public class InvalidLogLineException extends RuntimeException{

    public InvalidLogLineException(String message) {
        super(message);
    }
}
