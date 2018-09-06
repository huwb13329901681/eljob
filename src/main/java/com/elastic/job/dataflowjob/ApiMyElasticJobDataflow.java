package com.elastic.job.dataflowjob;


import com.elastic.job.service.User;
import com.elastic.job.service.UserService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;

import java.util.List;
import java.util.UUID;

/**
 * @author huwenbin
 *  Dataflow类型用于处理数据流 需实现Dataflow接口，该接口提供2个方法可覆盖，
 *  分别用于抓取(fetchData)和处理(processData)数据。
 */
@ElasticConfig(name = "ApiMyElasticJobDataflow",cron = "0/01 * * * * ?",shardingItemParameters = "0=beijing,1=shanghai",shardingTotalCount = 3,jobParameter="1")
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
        System.out.println("分片参数："+shardingContext.getJobParameter()+"分片总数："+shardingContext.getShardingTotalCount());
        List<User> list = Lists.newArrayList();
        User u1 = new User();
        u1.setId(UUID.randomUUID().toString()+"1");
        u1.setName("张三");
        u1.setSex("男");
        User u2 = new User();
        u2.setId(UUID.randomUUID().toString()+"2");
        u2.setName("李四");
        u2.setSex("男");
        list.add(u1);
        list.add(u2);
        System.out.println(userService+"1231231");
        return list;

    }

    /**
     * 拉取
     * @param shardingContext shardingContext
     * @param data 数据源
     */
    @Override
    public void processData(ShardingContext shardingContext, List<User> data) {
        System.out.println("执行从数据库查询到的list="+data);
    }
}
