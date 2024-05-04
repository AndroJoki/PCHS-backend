package com.PCHS.exceptions;

public class AlreadyExistException extends BaseException {
    public AlreadyExistException(String type) {
        super(type + " Already Exist!");
        this.setExceptionType(type);
        this.setStatusCode(409);
    }
}
