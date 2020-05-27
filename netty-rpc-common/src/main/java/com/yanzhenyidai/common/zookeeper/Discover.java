package com.yanzhenyidai.common.zookeeper;

import org.I0Itec.zkclient.ZkClient;

/**
 * @author frank
 * @version 1.0
 * @date 2020-05-13 17:07
 */
public class Discover {

    private String zkAddress = "localhost:2181";

    public String discover(String serviceName) {

        ZkClient zkClient = new ZkClient(zkAddress);

        Object o = zkClient.readData(serviceName);

        return o.toString();
    }

    public static void main(String[] args) {
        new Discover().discover("/dubbo/com.zynsun.platform.edoc.facade.scanTaskManage.EdocHeaderFacade/providers");
    }
}
