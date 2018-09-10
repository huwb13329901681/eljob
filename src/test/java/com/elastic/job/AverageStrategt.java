package com.elastic.job;

import com.elastic.lambda.JobStance;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.*;


/**
 * @author huwenbin
 *  1.平均分配策略
 *  2.通过工作名称的hashcode的值偶数倒叙
 *
 */
public class AverageStrategt {

    @Test
    public void shardingAliquot() {
        //3台服务器
        List<JobStance> shardingUnits = Lists.newArrayList();
        JobStance jobInstance1 = new JobStance();
        jobInstance1.setJobInstanceId(UUID.randomUUID().toString()+"1");
        JobStance jobInstance2 = new JobStance();
        jobInstance2.setJobInstanceId(UUID.randomUUID().toString()+"2");
        JobStance jobInstance3 = new JobStance();
        jobInstance3.setJobInstanceId(UUID.randomUUID().toString()+"3");
        shardingUnits.add(jobInstance1);
        shardingUnits.add(jobInstance2);
        shardingUnits.add(jobInstance3);
        // 将任务分成10片
        Integer shardingTotalCount = 10;
        Map<JobStance, List<Integer>> result = new LinkedHashMap<>(shardingTotalCount, 1);
        //获取值 10/3 = 1
        int itemCountPerSharding = shardingTotalCount / shardingUnits.size();
        int count = 0;
        for (JobStance each : shardingUnits) {
            List<Integer> shardingItems = new ArrayList<>(itemCountPerSharding + 1);
            for (int i = count * itemCountPerSharding; i < (count + 1) * itemCountPerSharding; i++) {
                shardingItems.add(i);
            }
            result.put(each, shardingItems);
            count++;
        }
        System.out.println("第一步："+result);
        // 不能平均配置
        addAliquant(shardingUnits, shardingTotalCount, result);
    }

    private void addAliquant(List<JobStance> shardingUnits, Integer shardingTotalCount, Map<JobStance, List<Integer>> result) {
        // 获取余数 5%3 = 2
        int aliquant = shardingTotalCount % shardingUnits.size();
        int count = 0;
        for (Map.Entry<JobStance, List<Integer>> entry : result.entrySet()) {
            if (count < aliquant) {
                entry.getValue().add(shardingTotalCount / shardingUnits.size() * shardingUnits.size() + count);
            }
            count++;
        }
        System.out.println("第二步："+result);
    }

}
