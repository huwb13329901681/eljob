package com.elastic.job.thread;

/**
 * @author huwenbin
 */
public class TestThreadDeadLock {

    public static void main(String[] args) {
        // 这里也可以不用 for 循环, 但可能需要尝试多次才能达到效果
        new Thread(new DeadLockRunnable(1, 2)).start();
        new Thread(new DeadLockRunnable(2, 1)).start();
    }
    static class DeadLockRunnable implements Runnable {
        final Integer a;
        final Integer b;
        DeadLockRunnable(Integer a, Integer b) {
            this.a = a;
            this.b = b;
        }
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
            synchronized (a) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (b) {
                    System.out.println("a + b = " + (a + b));
                }
            }
        }
    }
}
