package com.yanzhenyidai.config;

import com.yanzhenyidai.client.NettyClient;
import com.yanzhenyidai.common.http.Request;
import com.yanzhenyidai.common.http.Response;
import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.Proxy;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @author frank
 * @version 1.0
 * @date 2020-05-27 11:54
 */
public class Client {

    public <T> T create(final Class<?> cls) {

        return (T) Proxy.newProxyInstance(cls.getClassLoader(), new Class<?>[]{cls}, new InvocationHandler() {
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {

                Request request = new Request();
                request.setInterfaceName("/yanzhenyidai/" + cls.getName());
                request.setRequestId(UUID.randomUUID().toString());
                request.setParameter(objects);
                request.setMethodName(method.getName());
                request.setParameterTypes(method.getParameterTypes());

                Response response = new NettyClient().client(request);
                return response.getResult();
            }
        });
    }
}
