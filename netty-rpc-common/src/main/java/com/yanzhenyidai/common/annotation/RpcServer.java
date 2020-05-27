package com.yanzhenyidai.common.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author frank
 * @version 1.0
 * @date 2020-05-26 14:47
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
@Component
public @interface RpcServer {

    /**
     * 接口类, 用以接口注册
     *
     * @return
     */
    Class<?> cls();

    /**
     * 服务名称
     *
     * @return
     */
    String serverName() default "";

    /**
     * 服务版本
     *
     * @return
     */
    String version() default "";

}
