package com.yujing.crash.model;

import java.io.Serializable;

/**
 * 服务器返回给前端对象，status为fasle时候message为错误内容
 */
public class Resp implements Serializable {
    protected int code;
    protected String message;
    protected Object data;

    public Resp(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Resp(String message, Object data) {
        this.code = 0;
        this.message = message;
        this.data = data;
    }

    public Resp(String message) {
        this.code = -1;
        this.message = message;
        this.data = null;
    }

    public Resp(Object data) {
        this.code = 0;
        this.message = null;
        this.data = data;
    }

    public Resp() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
