package com.zk;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;


/**
 * @author Administrator
 * zkClient不是一次性的，客户端只需要注册一次就会一直生效
 */
public class GetChildrenSample {

    private static String path = "/zk-book";

    public static void main(String[] args) throws Exception{
        ZkClient zkClient = new ZkClient("192.168.24.120:2181",5000);
        // 对子节点的监听
        zkClient.subscribeChildChanges(path, new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                System.out.println(parentPath + " 's child changed, currentChilds:" + currentChilds);
            }
        });

        zkClient.createPersistent(path);
        Thread.sleep(1000);
        System.out.println(zkClient.getChildren(path));
        zkClient.createPersistent(path + "/cl");
        Thread.sleep(1000);
        zkClient.delete(path +"/cl");
        Thread.sleep(1000);
        zkClient.delete(path);
        Thread.sleep(Integer.MAX_VALUE);

    }
}
