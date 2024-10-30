package com.goruna.spring.common.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    ErrorCodeType errorCode;

    public CustomException(ErrorCodeType errorCode) {
        this.errorCode = errorCode;
    }

}
