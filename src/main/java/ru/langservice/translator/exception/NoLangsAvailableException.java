package ru.langservice.translator.exception;

public class NoLangsAvailableException extends RuntimeException {
    public NoLangsAvailableException() {
    }
    public NoLangsAvailableException(String message) {
        super(message);
    }
}
