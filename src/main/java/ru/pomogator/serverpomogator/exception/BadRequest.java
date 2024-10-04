package ru.pomogator.serverpomogator.exception;

import lombok.Getter;

import java.util.HashMap;

@Getter
public class BadRequest extends RuntimeException {
    private HashMap<String, String> errors;

    public BadRequest(String message) {
        super(message);
    }

    public BadRequest(String message, HashMap<String, String> errors) {
        super(message);
        this.errors = errors;
    }
}


