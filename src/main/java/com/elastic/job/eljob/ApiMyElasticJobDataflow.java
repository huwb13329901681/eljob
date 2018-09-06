package com.elastic.job.eljob;


import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.elastic.job.enable.ElasticJobConfig;
import com.elastic.job.service.User;
import com.elastic.job.service.UserService;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author huwenbin
 *  Dataflow类型用于处理数据流 需实现Dataflow接口，该接口提供2个方法可覆盖，
 *  分别用于抓取(fetchData)和处理(processData)数据。
 */
@ElasticConfig(name="ApiMyElasticJobDataflow",cron = "0/01 * * * * ?",jobParameter = "22",shardingTotalCount = 4,shardingItemParameters="1=男")
@Component
public class ApiMyElasticJobDataflow implements DataflowJob<User>{

    @Autowired
    private UserService userService;

    /**
     * 抓取
     * @param shardingContext 分片上下文
     * @return List<User>
     */
    @Override
    public List<User> fetchData(ShardingContext shardingContext) {
        User user = new User();
        System.out.println("1111111111="+shardingContext.getShardingParameter()+""+shardingContext.getJobParameter());
//        String jobParameter = shardingContext.getJobParameter();
//        int shardingTotalCount = shardingContext.getShardingTotalCount();
//        System.out.println("分片总数="+shardingTotalCount);
//        Map<String, String> map = Splitter.on(",").withKeyValueSeparator("=").split(jobParameter);
//        String strings = map.keySet().toString();
//        System.out.println("map="+map);
//        System.out.println("strings="+strings);
//        System.out.println("jobParameter="+jobParameter);
        return userService.getUserInfo(user);

    }

    /**
     * 拉取
     * @param shardingContext shardingContext
     * @param data 数据源
     */
    @Override
    public void processData(ShardingContext shardingContext, List<User> data) {
//        System.out.println("执行从数据库查询到的list="+data);
    }
}
