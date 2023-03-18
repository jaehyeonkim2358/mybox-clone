package com.clonecoding.myboxclone.exception;

public class AuthException extends CustomException {
    public AuthException(CustomErrorType errorType) {
        super(errorType);
    }
}
