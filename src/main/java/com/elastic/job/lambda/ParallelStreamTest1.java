package com.elastic.job.lambda;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author huwenbin
 */
public class ParallelStreamTest1 {
    public static void main(String[] args) {


        List<String> list = Lists.newArrayList();
        for (int i =0 ; i <1000000; i++){
            list.add("111"+i);
        }

        long start = System.currentTimeMillis();
        list.stream().
                filter(e -> StringUtils.isNotBlank(e)).
                collect(Collectors.toList());
        System.out.println("stream : " + (System.currentTimeMillis() - start) + "ms");

        start = System.currentTimeMillis();
        list.parallelStream().
                filter(e -> StringUtils.isNotBlank(e)).
                collect(Collectors.toList());
        System.out.println("parallelStream : " + (System.currentTimeMillis() - start) + "ms");
    }


    @Test
    public void test1(){
        List<String> list = Lists.newArrayList();
        for (int i =0 ; i <1000000; i++){
            list.add("111"+i);
        }

        long start = System.currentTimeMillis();
        list.stream().
                filter(e -> StringUtils.isNotBlank(e)).
                collect(Collectors.toList());
        System.out.println("stream : " + (System.currentTimeMillis() - start) + "ms");

    }

    @Test
    public void test2(){
        List<String> list = Lists.newArrayList();
        for (int i =0 ; i <1000000; i++){
            list.add("aaa"+i);
        }

        long start = System.currentTimeMillis();
        list.parallelStream().
                filter(e -> StringUtils.isNotBlank(e)).
                collect(Collectors.toList());
        System.out.println("parallelStream : " + (System.currentTimeMillis() - start) + "ms");
    }
}
