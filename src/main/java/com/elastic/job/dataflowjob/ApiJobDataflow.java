package com.elastic.job.dataflowjob;


import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.elastic.job.eljob.ApiMyElasticJobDataflow;

/**
 * 注册中心配置
 * 用于注册和协调作业分布式行为的组件，目前仅支持Zookeeper
 * @author huwenbin
 */
public class ApiJobDataflow {

//    public static void main(String[] args) {
//        new JobScheduler(createRegistryCenter(), createJobConfiguration()).init();
//    }
//
//    private static CoordinatorRegistryCenter createRegistryCenter() {
//        CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(new ZookeeperConfiguration("192.168.24.111:2181,192.168.24.112:2181,192.168.24.113:2181", "elastic-job-demo"));
//        regCenter.init();
//        return regCenter;
//    }
//
//    private static LiteJobConfiguration createJobConfiguration() {
//        // 定义作业核心配置
//        String shardingItemParameters = "0=Beijing,1=Shanghai,2=Guangzhou";
//        JobCoreConfiguration dataflowCoreConfig = JobCoreConfiguration.newBuilder("dataflowJob", "0/20 * * * * ?", 3).shardingItemParameters(shardingItemParameters).build();
//
//        // 定义DATAFLOW类型配置  true 是否是流试处理数据，如果是则fetchData不返回空结果将持续执行作业，如果非流式处理数据, 则处理数据完成后作业结束
//        DataflowJobConfiguration dataflowJobConfig = new DataflowJobConfiguration(dataflowCoreConfig, ApiMyElasticJobDataflow.class.getCanonicalName(), true);
//
//        // 定义Lite作业根配置
//        LiteJobConfiguration dataflowJobRootConfig= LiteJobConfiguration.newBuilder(dataflowJobConfig).build();
//
//        return dataflowJobRootConfig;
//    }
}
