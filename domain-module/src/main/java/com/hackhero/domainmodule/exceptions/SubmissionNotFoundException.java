package com.hackhero.domainmodule.exceptions;

public class SubmissionNotFoundException extends RuntimeException {
    public SubmissionNotFoundException(String message) {
        super(message);
    }
}
