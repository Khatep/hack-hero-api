package com.hackhero.authmodule.utils;

public interface ExceptionUtil {
    static Throwable getRootCause(Throwable throwable) {
        Throwable result = throwable;
        while (result.getCause() != null) {
            result = result.getCause();
        }
        return result;
    }
}
