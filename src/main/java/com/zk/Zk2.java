package com.zk;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * @author Administrator
 */
public class Zk2 implements Watcher{

    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private static ZooKeeper zooKeeper;
    /**
     * connectString : host:port,host:port
     * connectString: host:port,host:port/zk-book
     */
    public static void main(String[] args) throws Exception {
        zooKeeper = new ZooKeeper("192.168.24.120:2181/zk-book",500,new Zk2());
        countDownLatch.await();
        long sessionId = zooKeeper.getSessionId();
        byte[] sessionPasswd = zooKeeper.getSessionPasswd();
        //use illegal sessionId and sessionPassWd error
        zooKeeper = new ZooKeeper("192.168.24.120:2181/zk-book",500,new Zk2(),1L,"test".getBytes());
        //use correct sessionId and sessionPassWd ok
        zooKeeper = new ZooKeeper("192.168.24.120:2181/zk-book",500,new Zk2(),sessionId,sessionPasswd);
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
