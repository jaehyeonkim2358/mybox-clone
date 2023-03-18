package com.clonecoding.myboxclone.exception;

public enum MemberExceptionType implements CustomErrorType{
    NOT_FOUND_USER("NOT_FOUND_USER"),
    DUPLICATE_USER("DUPLICATE_USER"),
    WRONG_PASSWORD("WRONG_PASSWORD");

    private final String message;

    MemberExceptionType(String message) {
        this.message = message;
    }

    @Override
    public String getErrorCode() {
        return this.message;
    }
}
