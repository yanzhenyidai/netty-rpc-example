package com.yanzhenyidai.common.http;

/**
 * @author frank
 * @version 1.0
 * @date 2020-04-08 17:04
 */
public class Request {

    private String requestId;

    private Object parameter;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Object getParameter() {
        return parameter;
    }

    public void setParameter(Object parameter) {
        this.parameter = parameter;
    }
}
