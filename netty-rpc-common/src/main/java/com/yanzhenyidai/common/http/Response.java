package com.yanzhenyidai.common.http;

/**
 * @author frank
 * @version 1.0
 * @date 2020-04-08 17:05
 */
public class Response {

    private String requestId;

    private Object result;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
