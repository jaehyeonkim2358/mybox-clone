package com.clonecoding.myboxclone.exception;

public class MemberException extends CustomException {
    public MemberException(MemberExceptionType errorType) {
        super(errorType);
    }
}
