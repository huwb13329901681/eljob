package com.elastic.job.eljob;

import com.dangdang.ddframe.job.api.ElasticJob;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author huwenbin
 *
 *  @ConditionalOnBean（仅仅在当前上下文中存在某个对象时，才会实例化一个Bean）
 *  @ConditionalOnClass（某个class位于类路径上，才会实例化一个Bean）
 *  @ConditionalOnExpression（当表达式为true的时候，才会实例化一个Bean）
 *  @ConditionalOnMissingBean（仅仅在当前上下文中不存在某个对象时，才会实例化一个Bean）
 *  @ConditionalOnMissingClass（某个class类路径上不存在的时候，才会实例化一个Bean）
 *  @ConditionalOnNotWebApplication（不是web应用）
 *  @EnableConfigurationProperties @ConfigurationProperties注解主要用来把properties配置文件转化为bean来使用的而@EnableConfigurationProperties注解的作用是@ConfigurationProperties注解生效
 */
@Configuration
@ConditionalOnClass({ElasticJob.class})
@ConditionalOnBean(annotation = {ElasticConfig.class})
@EnableConfigurationProperties({ZookeeperRegistryProperties.class})
public class RegistryCenterConfiguration {
    private final ZookeeperRegistryProperties regCenterProperties;

    @Autowired
    public RegistryCenterConfiguration(ZookeeperRegistryProperties regCenterProperties) {
        this.regCenterProperties = regCenterProperties;
    }


    @Bean(initMethod = "init")
    @ConditionalOnMissingBean
    public ZookeeperRegistryCenter regCenter() {
        ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration(this.regCenterProperties.getZkAddressList(), this.regCenterProperties.getNamespace());
        zookeeperConfiguration.setBaseSleepTimeMilliseconds(this.regCenterProperties.getBaseSleepTimeMilliseconds());
        zookeeperConfiguration.setConnectionTimeoutMilliseconds(this.regCenterProperties.getConnectionTimeoutMilliseconds());
        zookeeperConfiguration.setMaxSleepTimeMilliseconds(this.regCenterProperties.getMaxSleepTimeMilliseconds());
        zookeeperConfiguration.setSessionTimeoutMilliseconds(this.regCenterProperties.getSessionTimeoutMilliseconds());
        zookeeperConfiguration.setMaxRetries(this.regCenterProperties.getMaxRetries());
        return new ZookeeperRegistryCenter(zookeeperConfiguration);
    }
}