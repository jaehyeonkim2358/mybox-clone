package com.clonecoding.myboxclone.exception;

import org.springframework.security.core.Authentication;

import javax.annotation.Resource;

public enum AuthExceptionType implements CustomErrorType{
    NOT_FOUND_AUTHORITY("NOT_FOUND_AUTHORITY");

    private final String message;

    AuthExceptionType(String message) {
        this.message = message;
    }

    @Override
    public String getErrorCode() {
        return this.message;
    }
}
