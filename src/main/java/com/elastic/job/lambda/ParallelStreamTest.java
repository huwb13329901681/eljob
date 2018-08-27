package com.elastic.job.lambda;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;
import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * @author huwenbin
 */
public class ParallelStreamTest {

    @Test
    public void parallelList1(){
        long startTime = System.currentTimeMillis();
        Long reduce = Stream.iterate(0L, i -> i + 1).limit(10000000).reduce(0L, Long::sum);
        long endTime = System.currentTimeMillis();
        System.out.println("使用"+ (endTime - startTime)+"毫秒的计算结果为："+reduce);
    }

    @Test
    public void parallelList2(){
        List<Long> list = Lists.newArrayList();
        for (Long m = 1L ; m< 10000001 ; m++){
            list.add(m);
        }
        long startTime = System.currentTimeMillis();
        Long reduce = list.parallelStream().reduce(0L, Long::sum);
        long endTime = System.currentTimeMillis();
        System.out.println("使用"+ (endTime - startTime)+"毫秒的计算结果为："+reduce);
    }

    @Test
    public void sequentialSum(){
        long startTime = System.currentTimeMillis();
        Stream.iterate(1L, i -> i+1).limit(100000000).reduce(0L,Long::sum);
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }

    @Test
    public void parellelSum(){
        long startTime = System.currentTimeMillis();
        Stream.iterate(1L, i -> i+1).limit(100000000).parallel().reduce(0L,Long::sum);
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }
    @Test
    public void iterativeSum(){
        long result = 0 ;
        for (long i =1L;i < 6 ;i++){
            result += i;
        }
        System.out.println(result);
    }

    @Test
    public  void rangedSum1(){
        long n = 1000L;
        long startTime = System.currentTimeMillis();
        long reduce = LongStream.rangeClosed(1, n).reduce(0L, Long::sum);
        long endTime = System.currentTimeMillis();
        System.out.println("经过"+(endTime - startTime)+"毫秒获取数据:"+reduce);
    }

    @Test
    public  void rangedSum2(){
        long n = 1000L;
        long startTime = System.currentTimeMillis();
        long reduce = LongStream.rangeClosed(1, n).parallel().reduce(0L, Long::sum);
        long endTime = System.currentTimeMillis();
        System.out.println("经过"+(endTime - startTime)+"毫秒获取数据:"+reduce);
    }

    public void measureSumPerf(Function<Long, Long> adder, long n) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            long sum = adder.apply(n);
            long duration = (System.nanoTime() - start) / 1_000_000;
            System.out.println("Result: " + sum);
            if (duration < fastest) {
                fastest = duration;
            }
        }
        System.out.println(fastest);
    }

    @Test
    public  void sideEffectParallelSum1(){
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, 6).forEach(acc ->{
            accumulator.add(acc);
        });
        System.out.println(accumulator.total);
    }

    @Test
    public  void sideEffectParallelSum2(){
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, 6).parallel().forEach(acc ->{
            accumulator.add(acc);
        });
        System.out.println(accumulator.total);
    }
}
