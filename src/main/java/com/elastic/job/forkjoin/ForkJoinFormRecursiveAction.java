package com.elastic.job.forkjoin;

import java.util.concurrent.*;

/**
 * @author huwenbin
 */
public class ForkJoinFormRecursiveAction extends RecursiveAction {

    private static final int THREAD_HOLD = 2;

    private int start;
    private int end;

    private ForkJoinFormRecursiveAction(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        //如果任务足够小就计算
        boolean canCompute = (end - start) <= THREAD_HOLD;
        if (canCompute){
            for (int i=start;i<end;i++){
                System.out.println(Thread.currentThread().getName()+"==i的值="+i);
            }
        }else{
            System.out.println("=======任务分解中===========");
            int middle = (start + end)/2;
            ForkJoinFormRecursiveAction left = new ForkJoinFormRecursiveAction(start,middle);
            ForkJoinFormRecursiveAction rigth = new ForkJoinFormRecursiveAction(middle+1,end);
            ForkJoinTask<Void> fork = left.fork();
            System.out.println("fork==" +fork);
            ForkJoinTask<Void> joinTask = rigth.fork();
            System.out.println("joinTask=="+joinTask);
        }
    }

    public static void main(String[] args) throws Exception {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinFormRecursiveAction forkJoinFormRecursiveAction = new ForkJoinFormRecursiveAction(1,4);

        // 提交任务
        forkJoinPool.submit(forkJoinFormRecursiveAction);

        // 阻塞当前线程直到forkJoinPool中所有的任务都执行结束
        forkJoinPool.awaitTermination(2, TimeUnit.SECONDS);

        // 关闭线程池
        forkJoinPool.shutdown();
    }
}
