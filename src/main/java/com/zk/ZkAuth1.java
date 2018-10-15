package com.zk;

import org.apache.zookeeper.*;

/**
 * @author Administrator
 *
 * zk的权限控制
 */
public class ZkAuth1 implements Watcher{

    private final static String PATH = "/zk-test-book-";

    public static void main(String[] args) throws Exception {
        ZooKeeper zk1 = new ZooKeeper("192.168.24.110:2181",5000,new ZkAuth1());
        zk1.addAuthInfo("digest","foo:true".getBytes());
        zk1.create(PATH,"init".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL,CreateMode.EPHEMERAL);

        ZooKeeper zk2 = new ZooKeeper("192.168.24.110:2181",50000,new ZkAuth1());
        zk2.addAuthInfo("digest","foo:true".getBytes());
        byte[] data = zk2.getData(PATH, false, null);
        System.out.println(data);

        ZooKeeper zk3 = new ZooKeeper("192.168.24.110:2181",50000,new ZkAuth1());
        zk3.addAuthInfo("digest","foo:false".getBytes());
        zk3.getData(PATH, false, null);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("12312");
    }
}
