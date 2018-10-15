package com.zk;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * @author Administrator
 * thread.join() 必须等待线程执行完毕，当前线程才能执行下去
 * 只有经查到位0
 * 使用zookeeper创建会话
 */
public class Zk1 implements Watcher{

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    /**
     * connectString : host:port,host:port
     * connectString: host:port,host:port/zk-book
     */
    public static void main(String[] args) throws Exception {
        ZooKeeper zooKeeper = new ZooKeeper("192.168.24.120:2181/zk-book",500,new Zk1());
        System.out.println(zooKeeper.getState());
        try{
            countDownLatch.await();
            Thread.sleep(2000);
            System.out.println("唤醒。。。");
        }catch (InterruptedException e){
            System.out.println("Zookeeper session established");
        }

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
