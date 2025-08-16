package com.hackhero.domainmodule.exceptions;

public class ChallengeNotFoundException extends RuntimeException {
    public ChallengeNotFoundException(String message) {
        super(message);
    }
}
