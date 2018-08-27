package com.elastic.job.simple;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

import java.util.Date;

/**
 * @author huwenbin
 */
import com.elastic.job.enable.ElasticJobConfig;

//@ElasticJobConfig(cron = "0/01 * * * * ?",shardingTotalCount=3,shardingItemParameters="0=北京,1=上海,2=广州")
public class ApiMyElasticJobSimple implements SimpleJob {

    /**
     * 执行作业
     * @param shardingContext 分片上下文
     */
    @Override
    public void execute(ShardingContext shardingContext) {

        System.out.println(new Date()+" 11111111 = "+shardingContext.getJobName()
                +"分片数量"+shardingContext.getShardingTotalCount()
                +"当前分区"+shardingContext.getShardingItem()
                +"当前分区名称"+shardingContext.getShardingParameter()
                +"当前自定义参数"+shardingContext.getJobParameter()+"============start=================");
    }
}
