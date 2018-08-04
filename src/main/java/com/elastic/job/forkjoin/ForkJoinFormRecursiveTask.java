package com.elastic.job.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * @author huwenbin
 * 通过ForkJoin求1+2+3+4
 */
public class ForkJoinFormRecursiveTask extends RecursiveTask<Integer> {

    private static final int THREAD_HOLD = 4;

    private int start;
    private int end;

    private ForkJoinFormRecursiveTask(int start, int end){
        this.start = start;
        this.end = end;
    }

    /**
     *  compute 需要判断任务是否足够小，如果足够小可以直接计算，如果不足够小就必须分割成2个子任务，
     *  每个子任务在调用fork的时候又回进入compute继续判断是否需要分割
     * @return Integer
     */
    @Override
    protected Integer compute() {
        int sum = 0;
        //如果任务足够小就计算
        boolean canCompute = (end - start) <= THREAD_HOLD;
        if(canCompute){
            for(int i=start;i<=end;i++){
                sum += i;
            }
        }else{
            System.out.println("=======任务分解中===========");
            int middle = (start + end) / 2;
            ForkJoinFormRecursiveTask left = new ForkJoinFormRecursiveTask(start,middle);
            ForkJoinFormRecursiveTask right = new ForkJoinFormRecursiveTask(middle+1,end);
            //执行子任务
            left.fork();
            right.fork();
            //获取子任务结果
            int lResult = left.join();
            int rResult = right.join();
            sum = lResult + rResult;
        }
        return sum;
    }

    public static void main(String[] args){
        // Math.min(MAX_CAP, Runtime.getRuntime().availableProcessors()),defaultForkJoinWorkerThreadFactory, null, false)
        int i = Runtime.getRuntime().availableProcessors();
        System.out.println(i);
        ForkJoinPool pool = new ForkJoinPool(4);
        ForkJoinFormRecursiveTask task = new ForkJoinFormRecursiveTask(1,4);
        Future<Integer> result = pool.submit(task);
        try {
            System.out.println(result.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
