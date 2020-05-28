# Netty-RPC

[![Build Status](https://travis-ci.com/yanzhenyidai/netty-rpc-example.svg?branch=master)](https://travis-ci.org/yanzhenyidai/netty-rpc-example)

> this project is sample for rpc process example.

---

## Module

 > Rpc-Common 
 
   this module include all of common class.
    
 > Rpc-Server
 
   this module include netty communication & zookeeper register.
 
 > Rpc-Client
 
   this module include netty communication & zookeeper discover.
 
 > Rpc-Sample
 
  - Rpc-sample-api
  
    just have interface class.
    
  - Rpc-sample-server
  
    implements `Rpc-sample-api` module interface. and start by SpringBoot.
  
  - Rpc-sample-client
  
    you can run this client for test Rpc.

---

## Process

 ![Rpc process.jpg](https://i.loli.net/2020/05/28/kyIRKz3UaATwmcu.png)
 
 look up.
 
 1. the provider start netty server and expose port.
 
 2. provider register to zookeeper, zk node is the interface class path, zk data is port, and object bean save to `handle` Map.
 
 3. run consumer, discover zookeeper by interface class. 
 
 4. get port and use interface class name.
 
 5. invoke netty client.
 
 6. netty client send request to provider and provider use `handle` Map object bean invoice interface class.
 
 7. netty sever return response data to netty client.
 
 8. consumer get data, this process is over.
    
--- 

## Website

 you can request [www.yanzhenyidai.com](www.yanzhenyidai.com) to get more information.