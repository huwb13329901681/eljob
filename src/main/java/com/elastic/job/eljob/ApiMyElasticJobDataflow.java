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

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author huwenbin
 *  Dataflow类型用于处理数据流 需实现Dataflow接口，该接口提供2个方法可覆盖，
 *  分别用于抓取(fetchData)和处理(processData)数据。
 */
@ElasticConfig(name="ApiMyElasticJobDataflow",cron = "0/05 * * * * ?",shardingTotalCount = 3)
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
        return userService.getUserInfo(user);

    }

    /**
     * 拉取
     * @param shardingContext shardingContext
     * @param data 数据源
     */
    @Override
    public void processData(ShardingContext shardingContext, List<User> data) {
        try {
            String address = InetAddress.getLocalHost().getHostAddress();
            System.out.println("111111执行从数据库查询到的list="+data);
            System.out.println("adress"+address+","+"ShardingParameter="+shardingContext.getShardingParameter()+","+"TaskId="+shardingContext.getTaskId()+",");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }
}
