package com.elastic.job.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author huwenbin
 */
public class CountTask extends RecursiveTask<Integer>{

    private static final long serialVersionUID = -3098662128710885597L;

    private static final int THRESHOLD =2 ;
    private int start;
    private int end;
    public CountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public static void main(String[] args) {

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTask countTask = new CountTask(1,4);
        Integer sum = forkJoinPool.invoke(countTask);
        //输出最终所有的计算结果
        System.out.println(sum);
        if (countTask.isCompletedAbnormally()){
            Throwable exception = countTask.getException();
            System.out.printf("Main:%s\n",exception);
        }
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        boolean canCompute  = (end - start) <= THRESHOLD;
        if (canCompute){
            for (int i = start ; i <=end;i++){
                sum +=i;
            }
        }else {
            int middle = (end  + start) / 2;
            if (middle <100){
                throw new RuntimeException("自己测试异常处理");
            }
            CountTask leftTask = new CountTask(start,middle);
            CountTask rightTask = new CountTask(middle+1,end);
            // 执行子任务
            leftTask.fork();
            rightTask.fork();
            // 等待子任务完成得到结果
            Integer left = leftTask.join();
            Integer right = rightTask.join();
            sum = left + right;
        }
        return sum;
    }
}
