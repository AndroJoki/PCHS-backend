package com.PCHS.exceptions;

public class AuthException extends BaseException {
    public AuthException() {
        super("Email or Password information is incorrect!");
        this.setExceptionType("Auth");
        this.setStatusCode(401);
    }
}
