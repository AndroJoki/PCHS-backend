package com.PCHS.exceptions;

public class MissingException extends BaseException {
    public MissingException(String type) {
        super(type + " is Missing!");
        this.setExceptionType(type);
        this.setStatusCode(404);
    }
}
