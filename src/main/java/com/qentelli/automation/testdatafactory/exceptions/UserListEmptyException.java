package com.qentelli.automation.testdatafactory.exceptions;

public class UserListEmptyException extends Exception {
    public UserListEmptyException (String message){
        super(message);
        throw new RuntimeException(message);
    }
}
