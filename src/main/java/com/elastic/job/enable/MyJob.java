package com.elastic.job.enable;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.elastic.job.service.User;
import com.elastic.job.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.UUID;

/**
 * @author huwenbin
 */
@Slf4j
@Component
@ElasticJobConfig(cron = "0/02 * * * * ?", shardingTotalCount=10,jobParameter = "fetchNum=1000")
public class MyJob implements DataflowJob<User> {

    @Autowired
    private UserService userService;


    /**
     * 拉取数据库列表
     * @return List
     */

    @Override
    public List<User> fetchData(ShardingContext shardingContext) {
        System.out.println("分片参数："+shardingContext.getJobParameter()+"分片总数："+shardingContext.getShardingTotalCount());
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        return userService.getUserInfo(user);
    }

    @Override
    public void processData(ShardingContext shardingContext, List<User> data) {
            System.out.println("执行从数据库查询到的list="+data);
    }
}
