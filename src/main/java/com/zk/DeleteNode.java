package com.zk;

import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

/**
 * @author Administrator
 */
public class DeleteNode implements Watcher{

    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private static ZooKeeper zooKeeper;
    /**
     * connectString : host:port,host:port
     * connectString: host:port,host:port/zk-book
     */
    public static void main(String[] args) throws Exception {
        zooKeeper = new ZooKeeper("192.168.24.110:2181",500,new DeleteNode());
        countDownLatch.await();
        zooKeeper.delete("/zk-test-book1-", 1);
        Thread.sleep(Integer.MAX_VALUE);
    }
    @Override
    public void process(WatchedEvent event) {
        System.out.println("Receive watched event:" + event);
        if (Event.KeeperState.SyncConnected == event.getState()){
            countDownLatch.countDown();
            System.out.println("减去1剩余："+countDownLatch.getCount());
        }
    }
}
