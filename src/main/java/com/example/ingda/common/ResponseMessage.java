package com.example.ingda.common;

import com.example.ingda.common.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMessage<T> {
    private String msg;
    private int statusCode;
    private T data;

    public ResponseMessage(String msg, int statusCode, T data){
        this.msg = msg;
        this.statusCode = statusCode;
        this.data = data;
    }

    public ResponseMessage(MessageCode messageCode, T data){
        this.msg = messageCode.getMsg();
        this.statusCode = MessageCode.SUCCESS.getStatusCode();
        this.data = data;
    }

    public ResponseMessage(ErrorCode errorCode, T data) {
        this.msg = errorCode.getMsg();
        this.statusCode = errorCode.getStatusCode();
        this.data = data;
    }

}
