package com.elastic.job.countdownlatch;

import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * @author Administrator
 */
public class TaskTool {

    public static void main(String[] args) {
        // 执行函数
        ExecFunc();
    }

    private static void ExecFunc() {
        try {
            debug("主线程开始时间 = " + new Date());
            // 主线程睡眠2秒种,防止CPU资源被耗尽
            Thread.sleep(2000);
            // 初始化countDown，几行数据就代表有几个线程
            CountDownLatch countDownLatch = new CountDownLatch(10);

            for (int i = 0; i < 10; i++) {
                // 执行线程
                TimeingThread timeingThread = new TimeingThread(countDownLatch);
                // 线程加入等待
                timeingThread.start();
            }
            // 等待子线程结束,开始主线程
            countDownLatch.await();
            debug("主线程结束时间 = " + new Date());
        } catch (Exception e) {
            debug("系统异常 = " + e.getMessage());
        }
    }

    private static class TimeingThread extends Thread{
        private CountDownLatch threadsSignal;
        TimeingThread(CountDownLatch threadsSignal) {

            this.threadsSignal = threadsSignal;
        }
        @Override
        public void run() {
            StartFuncJob();
        }
        private void StartFuncJob() {
            try {
                for (int i = 0; i < 3; i++) {
                    debug(Thread.currentThread().getName() + " ------------数数 ==" + i);
                    Thread.sleep(1000);
                }
                debug(Thread.currentThread().getName() + " ------------数完了-----------------------------------------");
            } catch (Exception ex) {
            } finally {
                // 计数器减少1
                threadsSignal.countDown();
                System.out.println("剩余数："+threadsSignal.getCount());
            }
        }
    }
    public static void debug(Object obj) {
        System.out.println(obj);
    }
}
