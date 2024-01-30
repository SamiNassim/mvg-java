package com.saminassim.mvgjava.exception;

public class BookCannotBeModifiedException extends RuntimeException {
    public BookCannotBeModifiedException(String message) {
        super(message);
    }
}