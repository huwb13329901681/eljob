package com.elastic.job;

import com.elastic.lambda.JobStance;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author huwenbin
 * 对服务表轮转的分片策略
 */
public class RotateServerStrategy {

    @Test
    public void test(){

        //6台服务器
        List<JobStance> shardingUnits = Lists.newArrayList();
        JobStance jobInstance1 = new JobStance();
        jobInstance1.setJobInstanceId(UUID.randomUUID().toString()+"1");
        JobStance jobInstance2 = new JobStance();
        jobInstance2.setJobInstanceId(UUID.randomUUID().toString()+"2");
        JobStance jobInstance3 = new JobStance();
        jobInstance3.setJobInstanceId(UUID.randomUUID().toString()+"3");
        JobStance jobInstance4 = new JobStance();
        jobInstance4.setJobInstanceId(UUID.randomUUID().toString()+"4");
        JobStance jobInstance5 = new JobStance();
        jobInstance5.setJobInstanceId(UUID.randomUUID().toString()+"5");
        JobStance jobInstance6 = new JobStance();
        jobInstance6.setJobInstanceId(UUID.randomUUID().toString()+"6");
        shardingUnits.add(jobInstance1);
        shardingUnits.add(jobInstance2);
        shardingUnits.add(jobInstance3);
        shardingUnits.add(jobInstance4);
        shardingUnits.add(jobInstance5);
        shardingUnits.add(jobInstance6);
        // 工作空间名称
        String jobName = "eljob1111";
        // 服务器个数
        int shardingUnitsSize = shardingUnits.size();
        int hashCode = jobName.hashCode();
        // 求hashcode的绝对值
        int offset = Math.abs(hashCode) % shardingUnitsSize;
        if (0 == offset) {
            System.out.println(shardingUnits);
            return;
        }
        List<JobStance> result = new ArrayList<>(shardingUnitsSize);
        for (int i = 0; i < shardingUnitsSize; i++) {
            int index = (i + offset) % shardingUnitsSize;
            result.add(shardingUnits.get(index));
        }
        System.out.println(result);
        return ;
    }
}
