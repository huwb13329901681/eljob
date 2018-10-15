package com.zk;

import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

/**
 * 使用异步asynchronous创建节点  使用接口StringCallback
 *
 * @author Administrator
 */
public class CreateNodeAsy implements Watcher{

    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private static ZooKeeper zooKeeper;
    /**
     * connectString : host:port,host:port
     * connectString: host:port,host:port/zk-book
     */
    public static void main(String[] args) throws Exception {
        zooKeeper = new ZooKeeper("192.168.24.110:2181",500,new CreateNodeAsy());
        countDownLatch.await();
        zooKeeper.create("/zk-test-book-", "789".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL,new IStringCallback(),"I am context");
        zooKeeper.create("/zk-test-book-", "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL,new IStringCallback(),"I am context");
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

    public static class IStringCallback implements AsyncCallback.StringCallback{

        /**
         *
         * @param rc Result Code 0:OK,-4:客户端和服务器连接断开,-110：指定节点已经存在,-112：会话过期
         * @param path 节点的路径
         * @param ctx  调接口传入的ctx参数（用于传递一个对象，可以在回调方法执行的时候使用，通常是放一个上下文信息）
         * @param name name
         */
        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            System.out.println("Create path result: [" + rc + "," + path + "," + ctx + ",real path name:"+name +"]");
        }
    }
}
