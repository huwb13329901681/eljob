package com.elastic.job.eljob;


import com.dangdang.ddframe.job.api.ElasticJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.api.strategy.impl.AverageAllocationJobShardingStrategy;
import com.dangdang.ddframe.job.lite.api.strategy.impl.OdevitySortByNameJobShardingStrategy;
import com.dangdang.ddframe.job.lite.api.strategy.impl.RotateServerByNameJobShardingStrategy;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;

/**
 * 注册中心配置
 * 用于注册和协调作业分布式行为的组件，目前仅支持Zookeeper
 * @author huwenbin
 */
//@Configuration
//@ConditionalOnExpression("'${elaticjob.zk.zkAddressList}'.length() > 0")
public class ApiJobDataflowDemo {

    @Resource
    private  ApplicationContext applicationContext;
    @Resource
    private ZookeeperRegistryCenter regCenter;

    @PostConstruct
    private  void createJobConfiguration() {
        // 定义作业核心配置
        Map<String, Object> elasticJobMap = applicationContext.getBeansWithAnnotation(ElasticConfig.class);
        for (Object elasticJobMBean:elasticJobMap.values()){
            ElasticJob elasticJob = (ElasticJob)elasticJobMBean;
            Class<?> clz = elasticJobMBean.getClass();
            ElasticConfig conf = clz.getAnnotation(ElasticConfig.class);
            String jobTypeName = elasticJobMBean.getClass().getInterfaces()[0].getSimpleName();
            String name = clz.getName();
            JobCoreConfiguration dataflowCoreConfig = JobCoreConfiguration.newBuilder(name,conf.cron(), conf.shardingTotalCount()).jobParameter(conf.jobParameter()).shardingItemParameters(conf.shardingItemParameters()).failover(conf.failover()).build();
            // 定义Lite作业根配置
            JobTypeConfiguration typeConfig = null;
            if ("SimpleJob".equals(jobTypeName)) {
                typeConfig = new SimpleJobConfiguration(dataflowCoreConfig, clz.getCanonicalName());
            }
            if ("DataflowJob".equals(jobTypeName)) {
                typeConfig = new DataflowJobConfiguration(dataflowCoreConfig, clz.getCanonicalName(), true);
            }
            // 平均分片
            String jobShardingStrategyClass = AverageAllocationJobShardingStrategy.class.getCanonicalName();
            // hashcode
            String canonicalName = OdevitySortByNameJobShardingStrategy.class.getCanonicalName();
            String canonicalName1 = RotateServerByNameJobShardingStrategy.class.getCanonicalName();
            LiteJobConfiguration liteJobConfiguration= LiteJobConfiguration.newBuilder(typeConfig).jobShardingStrategyClass(canonicalName).build();
            new SpringJobScheduler(elasticJob,regCenter, liteJobConfiguration).init();
        }
    }

}
