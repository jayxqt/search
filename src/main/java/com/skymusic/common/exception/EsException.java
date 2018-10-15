package com.skymusic.common.exception;

import com.skymusic.common.enums.ErrorCode;

/**
 * 异常类
 * @author xqt
 */
public class EsException extends RuntimeException {

    private static final long serialVersionUID=1L;

    private ErrorCode errorCode;

    public EsException() {
        super();
    }

    public EsException(String message) {
        super(message);
    }

    public EsException(ErrorCode errorCode) {
        this.errorCode=errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode=errorCode;
    }

}