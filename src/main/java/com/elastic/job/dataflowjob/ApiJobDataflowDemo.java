package com.elastic.job.dataflowjob;


import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.script.ScriptJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Map;

/**
 * 注册中心配置
 * 用于注册和协调作业分布式行为的组件，目前仅支持Zookeeper
 * @author huwenbin
 */
@Configuration
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
            Class<?> clz = elasticJobMBean.getClass();
            ElasticConfig conf = clz.getAnnotation(ElasticConfig.class);
            String jobTypeName = elasticJobMBean.getClass().getInterfaces()[0].getSimpleName();
//            JobEventRdbConfiguration jobEventRdbConfiguration = this.getJobEventRdbConfiguration(conf.eventTraceRdbDataSource());
            String jobName = conf.name();
            JobCoreConfiguration dataflowCoreConfig = JobCoreConfiguration.newBuilder(jobName, conf.cron(), conf.shardingTotalCount()).shardingItemParameters(conf.shardingItemParameters()).build();

        // 定义DATAFLOW类型配置  true 是否是流试处理数据，如果是则fetchData不返回空结果将持续执行作业，如果非流式处理数据, 则处理数据完成后作业结束
        DataflowJobConfiguration dataflowJobConfig = new DataflowJobConfiguration(dataflowCoreConfig, clz.getCanonicalName(), true);

        // 定义Lite作业根配置
            JobTypeConfiguration typeConfig = null;
            if ("SimpleJob".equals(jobTypeName)) {
                typeConfig = new SimpleJobConfiguration(dataflowCoreConfig, jobName);
            }
            if ("DataflowJob".equals(jobTypeName)) {
                typeConfig = new DataflowJobConfiguration(dataflowCoreConfig, jobName, true);
            }

        LiteJobConfiguration dataflowJobRootConfig= LiteJobConfiguration.newBuilder(typeConfig).build();
        new JobScheduler(regCenter, dataflowJobRootConfig).init();
        }
    }


}
