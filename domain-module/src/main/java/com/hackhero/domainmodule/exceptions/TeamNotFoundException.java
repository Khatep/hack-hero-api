package com.hackhero.domainmodule.exceptions;

public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException(String message) {
        super(message);
    }
}
