package ru.pomogator.serverpomogator.exception;

import java.util.List;
import java.util.Map;

public class BadRequest extends RuntimeException {
    public BadRequest(String message) {
        super(message);
    }
}


