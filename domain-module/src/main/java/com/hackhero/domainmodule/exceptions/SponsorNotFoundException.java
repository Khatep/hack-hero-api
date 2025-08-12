package com.hackhero.domainmodule.exceptions;

public class SponsorNotFoundException extends RuntimeException {
    public SponsorNotFoundException(String message) {
        super(message);
    }
}
