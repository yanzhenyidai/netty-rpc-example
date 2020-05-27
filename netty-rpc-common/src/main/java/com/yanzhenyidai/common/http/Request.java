package com.yanzhenyidai.common.http;

/**
 * @author frank
 * @version 1.0
 * @date 2020-04-08 17:04
 */
public class Request {

    private String requestId;

    private String interfaceName;

    private String methodName;

    private Class<?>[] parameterTypes;

    private Object parameter[];

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameter() {
        return parameter;
    }

    public void setParameter(Object[] parameter) {
        this.parameter = parameter;
    }
}
