package com.zk;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

/**
 * @author Administrator
 * zk 需要先删除子节点，在删除父节点
 */
public class AuthSampleDelete {
    final static String PATH = "/zk-book-auth_test";
    final static String PATH2 = "/zk-book-auth_test/child";

    public static void main(String[] args) throws Exception {

        ZooKeeper zookeeper1 = new ZooKeeper("192.168.24.110:2181", 50000, null);
        zookeeper1.addAuthInfo("digest", "foo:true".getBytes());
        zookeeper1.create(PATH, "init".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
        zookeeper1.create(PATH2, "init".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);
        try {

            ZooKeeper zookeeper2 = new ZooKeeper("192.168.24.110:2181", 50000, null);
            zookeeper2.delete(PATH2, -1);
        } catch (Exception e) {
            System.out.println("删除节点失败：" + e.getMessage());

        }
        try{
            ZooKeeper zookeeper3 = new ZooKeeper("192.168.24.110:2181", 50000, null);
            zookeeper3.addAuthInfo("digest", "foo:true".getBytes());
            zookeeper3.delete(PATH, -1);
            System.out.println("成功删除节点：" + PATH2);
        }catch (Exception e) {
            System.out.println("删除节点失败：" + e.getMessage());
        }

        try{
            ZooKeeper zookeeper4 = new ZooKeeper("192.168.24.110:2181", 50000, null);
            zookeeper4.delete(PATH2, -1);
            System.out.println("成功删除节点：" + PATH);
        }catch (Exception e) {
            System.out.println("删除节点失败：" + e.getMessage());
        }

    }

}