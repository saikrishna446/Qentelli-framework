package com.qentelli.automation.testdatafactory.exceptions;

public class TestIsNotReadyException extends Exception {
    public TestIsNotReadyException(String message){
        super(message);
        throw new RuntimeException(message);
    }
}
