package com.hackhero.domainmodule.exceptions;

public class HackathonNotFoundException extends RuntimeException {
    public HackathonNotFoundException(String message) {
        super(message);
    }
}
