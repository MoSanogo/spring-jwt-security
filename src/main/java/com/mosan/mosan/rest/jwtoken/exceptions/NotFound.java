package com.mosan.mosan.rest.jwtoken.exceptions;

public class NotFound  extends Exception{
    public NotFound(String message) {
        super(message);
    }
    public NotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
