package com.clonecoding.myboxclone.exception;

import java.io.Serializable;

public class CustomException extends RuntimeException implements Serializable {
    private final CustomErrorType errorType;

    protected CustomException(CustomErrorType errorType) {
        super(errorType.getErrorCode());
        this.errorType = errorType;
    }

    public CustomErrorType getBaseErrorType() {
        return this.errorType;
    }
}
