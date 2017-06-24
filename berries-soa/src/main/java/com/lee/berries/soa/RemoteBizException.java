package com.lee.berries.soa;

import java.io.Serializable;

/**
 * @author 黄奕鹏(大鹏)
 * SOA调用业务标准错误异常
 */
public class RemoteBizException extends RuntimeException implements Serializable {
    /**
     * 错误代码
     */
    private int code;
    public RemoteBizException(String msg){
        super(msg);
    }

    public RemoteBizException(String msg,int code){
        super(msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
