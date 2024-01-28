package com.saminassim.mvgjava.exception;

public class BookAlreadyRatedException extends RuntimeException {
    public BookAlreadyRatedException(String message) {
        super(message);
    }
}