package com.bjit.traineeselectionsystem.exception;

public class EntryNotFoundException extends RuntimeException{
    public EntryNotFoundException(String message) {
        super(message);
    }

    public EntryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }



}
