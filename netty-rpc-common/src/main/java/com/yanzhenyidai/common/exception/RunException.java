package com.yanzhenyidai.common.exception;

/**
 * @author frank
 * @version 1.0
 * @date 2020-05-28 11:08
 */
public class RunException extends Exception {

    public RunException(String message) {
        super(message);
    }

    public RunException(String message, Throwable cause) {
        super(message, cause);
    }

    public RunException(Throwable cause) {
        super(cause);
    }

    protected RunException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}