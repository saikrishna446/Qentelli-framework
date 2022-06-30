package com.qentelli.automation.testdatafactory.exceptions;

public class NoTestIDLineInFeatureException extends Exception{
    public NoTestIDLineInFeatureException(String message){
        super(message);
        throw new RuntimeException(message);

    }
}
