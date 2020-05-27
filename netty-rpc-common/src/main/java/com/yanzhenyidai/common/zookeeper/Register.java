package com.yanzhenyidai.common.zookeeper;

import org.I0Itec.zkclient.ZkClient;

/**
 * @author frank
 * @version 1.0
 * @date 2020-05-13 16:46
 */
public class Register {

    private String zkAddress = "localhost:2181";

    public void register(String serviceName, Object serviceAddress) {
        ZkClient zkClient = new ZkClient(zkAddress);

        String[] path = serviceName.split("/");

        if (!zkClient.exists("/" + path[1])) {
            zkClient.createPersistent("/" + path[1]);
        }

        if (!zkClient.exists(serviceName)) {
            zkClient.createPersistent(serviceName, serviceAddress);
        }

        zkClient.createEphemeralSequential(serviceName + "-hi", serviceAddress);
    }
}
