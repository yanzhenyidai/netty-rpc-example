package com.yanzhenyidai.config;

import com.yanzhenyidai.common.annotation.RpcServer;
import com.yanzhenyidai.server.NettyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author frank
 * @version 1.0
 * @date 2020-05-26 15:15
 */
@Component
public class Server implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    @Value("${register.address}")
    private String registerAddress;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> serviceBean = new HashMap<String, Object>();

        Map<String, Object> objectMap = applicationContext.getBeansWithAnnotation(RpcServer.class);

        for (Object object : objectMap.values()) {
            try {
                RpcServer annotation = object.getClass().getAnnotation(RpcServer.class);

                serviceBean.put("/yanzhenyidai/" + annotation.cls().getName(), object);

                String[] split = registerAddress.split(":");

                new NettyServer(split[0], Integer.valueOf(split[1])).server(serviceBean);
            } catch (Exception e) {
                logger.error("[server-start] fail ", e);
            }
        }
    }
}
