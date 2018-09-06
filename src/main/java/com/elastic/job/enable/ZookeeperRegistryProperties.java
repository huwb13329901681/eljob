package com.elastic.job.enable;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author hwb
 */
@ConfigurationProperties(prefix = "elaticjob.zk")
@Data
public class ZookeeperRegistryProperties {
    private String zkAddressList;
    private String namespace;
    private int baseSleepTimeMilliseconds = 1000;
    private int maxSleepTimeMilliseconds = 3000;
    private int maxRetries = 3;
    private int connectionTimeoutMilliseconds = 15000;
    private int sessionTimeoutMilliseconds = 60000;
    private String digest;



}
