package com.elastic.job.simple;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author huwenbin
 * 实现一个分布式的任务监听器，如果任务有分片，分布式监听器会在总的任务开始前执行一次，结束时执行一次
 */
@Service
public class ElasticJobListenerInfo implements ElasticJobListener {


    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        System.out.println("准备执行任务1111。。。。。。。。。。");
    }

    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        System.out.println("任务执行结束3333。。。。。。。。。。");
    }
}
