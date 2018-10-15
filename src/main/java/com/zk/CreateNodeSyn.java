package com.zk;

import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

/**
 *  synchronous 同步
 * @author Administrator
 */
public class CreateNodeSyn implements Watcher{

    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private static ZooKeeper zooKeeper;
    /**
     * connectString : host:port,host:port
     * connectString: host:port,host:port/zk-book
     */
    public static void main(String[] args) throws Exception {
        zooKeeper = new ZooKeeper("192.168.24.120:2181",500,new CreateNodeSyn());
        countDownLatch.await();
        String path = zooKeeper.create("/zk-test-book-", "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("Success create zNode:" + path);
        String path2 = zooKeeper.create("/zk-test-book-", "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("Success create zNode:" + path2);
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
