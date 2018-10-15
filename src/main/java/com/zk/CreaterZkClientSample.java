package com.zk;

import org.I0Itec.zkclient.ZkClient;

/**
 * @author Administrator
 *
 * 使用zkClient创建会话与zookeeper不同的是
 * 1.原生接口只能传入byte[] 而zkClient可以传入复杂的参数
 * 2.zookeeper不能递归创建节点，zkClient可以createPersistent
 * 3.zkClient构造函数中不在传入wacher，可以使用listen进行对服务的监听
 * 4.zkClient 中的删除deleteRecurisve(String path) 可以直接删除父节点包括下面所有的子节点
 * 5.zkClient 中的listen不是一次性的，客户端只需要注册一次就会一直生效
 */
public class CreaterZkClientSample {

    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient("192.168.24.110:2181",5000);
        // true 表明使用递归创建
        zkClient.createPersistent("zkClient1",true);
        System.out.println(zkClient);
    }
}
