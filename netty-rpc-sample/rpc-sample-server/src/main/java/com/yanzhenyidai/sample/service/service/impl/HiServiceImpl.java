package com.yanzhenyidai.sample.service.service.impl;

import com.yanzhenyidai.common.annotation.RpcServer;
import com.yanzhenyidai.sample.service.HiService;

/**
 * @author frank
 * @version 1.0
 * @date 2020-05-26 15:09
 */
@RpcServer(cls = HiService.class)
public class HiServiceImpl implements HiService {

    public String hi(String msg) {
        return "hello, I'm Rpc, I want say : " + msg;
    }
}
