package com.yanzhenyidai;

import com.yanzhenyidai.config.Client;
import com.yanzhenyidai.sample.service.HiService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author frank
 * @version 1.0
 * @date 2020-05-27 11:53
 */
public class RpcClientApplication {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");

        Client client = context.getBean(Client.class);

        HiService hiService = client.create(HiService.class);
        String msg = hiService.hi("msg");
        System.out.println(msg);

    }
}
