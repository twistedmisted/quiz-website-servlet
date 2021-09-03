package com.epam.final_project.exception;

public class NotFoundCommandException extends RuntimeException {

    public NotFoundCommandException(String message) {
        super(message);
    }

    public NotFoundCommandException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
