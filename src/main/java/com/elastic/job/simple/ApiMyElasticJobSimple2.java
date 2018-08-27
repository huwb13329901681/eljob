package com.elastic.job.simple;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.elastic.job.enable.ElasticJobConfig;

import java.util.Date;

/**
 * @author huwenbin  * 0/20 * * * ?
 */

//@ElasticJobConfig(cron = "0/02 * * * * ?",shardingTotalCount=3,shardingItemParameters="0=a,1=b")
public class ApiMyElasticJobSimple2 implements SimpleJob {

    /**
     * 执行作业
     * @param shardingContext 分片上下文
     */
    @Override
    public void execute(ShardingContext shardingContext) {

        System.out.println(new Date()+" 2222222222 = "+shardingContext.getJobName()
                +"分片数量"+shardingContext.getShardingTotalCount()
                +"当前分区"+shardingContext.getShardingItem()
                +"当前分区名称"+shardingContext.getShardingParameter()
                +"当前自定义参数"+shardingContext.getJobParameter()+"============start=================");
    }
}
