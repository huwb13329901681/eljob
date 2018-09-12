package com.elastic.job.eljob;


import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.elastic.job.service.User;
import com.elastic.job.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author huwenbin
 */
//@ElasticConfig(name="SimpleJob",cron = "0/03 * * * * ?")
//@Component
@Slf4j
public class ApiMyElasticJobSimpleJob implements SimpleJob {

    @Autowired
    private UserService userService;


    @Override
    public void execute(ShardingContext shardingContext) {
        User user = new User();
        List<User> userInfo = userService.getUserInfo(user);
        log.info("ssssssssssssssssssssssssssss+SimpleJob"+userInfo);
    }
}
