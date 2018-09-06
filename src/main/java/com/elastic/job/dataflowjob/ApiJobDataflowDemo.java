package com.elastic.job.dataflowjob;


import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.api.listener.AbstractDistributeOnceElasticJobListener;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.elastic.job.enable.ElasticJobConfig;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 注册中心配置
 * 用于注册和协调作业分布式行为的组件，目前仅支持Zookeeper
 * @author huwenbin
 */
@Configuration
@ConditionalOnExpression("'${elaticjob.zk.zkAddressList}'.length() > 0")
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
            JobCoreConfiguration dataflowCoreConfig = JobCoreConfiguration.newBuilder(conf.name(), conf.cron(), conf.shardingTotalCount()).shardingItemParameters(conf.shardingItemParameters()).build();

        // 定义DATAFLOW类型配置  true 是否是流试处理数据，如果是则fetchData不返回空结果将持续执行作业，如果非流式处理数据, 则处理数据完成后作业结束
        DataflowJobConfiguration dataflowJobConfig = new DataflowJobConfiguration(dataflowCoreConfig, clz.getCanonicalName(), true);
            ElasticJobListener[] elasticJobListeners = this.createElasticJobListeners(conf);
        // 定义Lite作业根配置
            JobTypeConfiguration typeConfig = null;
            if ("SimpleJob".equals(jobTypeName)) {
                typeConfig = new SimpleJobConfiguration(dataflowCoreConfig, clz.getCanonicalName());
            }
            if ("DataflowJob".equals(jobTypeName)) {
                typeConfig = new DataflowJobConfiguration(dataflowCoreConfig, clz.getCanonicalName(), true);
            }
            LiteJobConfiguration liteJobConfiguration= LiteJobConfiguration.newBuilder(typeConfig).build();
            elasticJobListeners = Objects.isNull(elasticJobListeners) ? new ElasticJobListener[0] : elasticJobListeners;
            new JobScheduler(regCenter, liteJobConfiguration).init();
        }
    }

    private ElasticJobListener[] createElasticJobListeners(ElasticConfig lasticConfig) {
        List<ElasticJobListener> elasticJobListeners = new ArrayList(2);
        ElasticJobListener elasticJobListener = this.createElasticJobListener(lasticConfig.listener());
        if (Objects.nonNull(elasticJobListener)) {
            elasticJobListeners.add(elasticJobListener);
        }
        AbstractDistributeOnceElasticJobListener distributedListener = this.createAbstractDistributeOnceElasticJobListener(lasticConfig.distributedListener(), lasticConfig.startedTimeoutMilliseconds(), lasticConfig.completedTimeoutMilliseconds());
        if (Objects.nonNull(distributedListener)) {
            elasticJobListeners.add(distributedListener);
        }
        if (CollectionUtils.isEmpty(elasticJobListeners)) {
            return null;
        } else {
            ElasticJobListener[] elasticJobListenerArray = new ElasticJobListener[elasticJobListeners.size()];
            for(int i = 0; i < elasticJobListeners.size(); ++i) {
                elasticJobListenerArray[i] = elasticJobListeners.get(i);
            }
            return elasticJobListenerArray;
        }
    }

    private ElasticJobListener createElasticJobListener(Class<? extends ElasticJobListener> listener) {
        if (listener.isInterface()) {
            return null;
        } else {
            return this.applicationContext.containsBean(listener.getSimpleName()) ? this.applicationContext.getBean(listener.getSimpleName(), ElasticJobListener.class) : this.registerElasticJobListener(listener);
        }
    }

    private AbstractDistributeOnceElasticJobListener createAbstractDistributeOnceElasticJobListener(Class<? extends AbstractDistributeOnceElasticJobListener> distributedListener, long startedTimeoutMilliseconds, long completedTimeoutMilliseconds) {
        if (Objects.equals(distributedListener, AbstractDistributeOnceElasticJobListener.class)) {
            return null;
        } else {
            return this.applicationContext.containsBean(distributedListener.getSimpleName()) ? this.applicationContext.getBean(distributedListener.getSimpleName(), AbstractDistributeOnceElasticJobListener.class) : this.registerAbstractDistributeOnceElasticJobListener(distributedListener, startedTimeoutMilliseconds, completedTimeoutMilliseconds);
        }
    }

    private ElasticJobListener registerElasticJobListener(Class<? extends ElasticJobListener> listener) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(listener);
        beanDefinitionBuilder.setScope("prototype");
        this.getDefaultListableBeanFactory().registerBeanDefinition(listener.getSimpleName(), beanDefinitionBuilder.getBeanDefinition());
        return this.applicationContext.getBean(listener.getSimpleName(), listener);
    }

    private AbstractDistributeOnceElasticJobListener registerAbstractDistributeOnceElasticJobListener(Class<? extends AbstractDistributeOnceElasticJobListener> distributedListener, long startedTimeoutMilliseconds, long completedTimeoutMilliseconds) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(distributedListener);
        beanDefinitionBuilder.setScope("prototype");
        beanDefinitionBuilder.addConstructorArgValue(startedTimeoutMilliseconds);
        beanDefinitionBuilder.addConstructorArgValue(completedTimeoutMilliseconds);
        this.getDefaultListableBeanFactory().registerBeanDefinition(distributedListener.getSimpleName(), beanDefinitionBuilder.getBeanDefinition());
        return this.applicationContext.getBean(distributedListener.getSimpleName(), distributedListener);
    }

    private DefaultListableBeanFactory getDefaultListableBeanFactory() {
        return (DefaultListableBeanFactory)((ConfigurableApplicationContext)this.applicationContext).getBeanFactory();
    }
}
