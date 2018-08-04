package com.elastic.job.dataflowjob;

import com.dangdang.ddframe.job.lite.api.strategy.JobInstance;
import com.dangdang.ddframe.job.lite.api.strategy.JobShardingStrategy;

import java.util.*;

public class ApiJobShardingStrategy implements JobShardingStrategy {

    /**
     * 作业分片.
     *
     * @param jobInstances 所有参与分片的单元列表
     * @param jobName 作业名称
     * @param shardingTotalCount 分片总数
     * @return 分片结果
     */
    @Override
    public Map<JobInstance, List<Integer>> sharding(List<JobInstance> jobInstances, String jobName, int shardingTotalCount) {
        // 不存在运行实例
        if (jobInstances.isEmpty()){
           return Collections.emptyMap();
        }
        // 分配能被整除的部分
        Map<JobInstance, List<Integer>> result = shardingAliquot(jobInstances, shardingTotalCount);
        // 分配不能被整除的部分
        addAliquant(jobInstances, shardingTotalCount, result);
        return result;
    }

    private void addAliquant(List<JobInstance> jobInstances, int shardingTotalCount, Map<JobInstance, List<Integer>> result) {
        // 余数
        int aliquant = shardingTotalCount % jobInstances.size();
        int count = 0;
        for (Map.Entry<JobInstance, List<Integer>> entry : result.entrySet()) {
            if (count < aliquant) {
                entry.getValue().add(shardingTotalCount / jobInstances.size() * jobInstances.size() + count);
            }
            count++;
        }
    }

    private Map<JobInstance,List<Integer>> shardingAliquot(List<JobInstance> jobInstances, int shardingTotalCount) {
        Map<JobInstance, List<Integer>> result = new LinkedHashMap<>(shardingTotalCount, 1);
        // 每个作业运行实例分配的平均分片数
        int itemCountPerSharding = shardingTotalCount / jobInstances.size();
        int count = 0;
        for (JobInstance each : jobInstances) {
            List<Integer> shardingItems = new ArrayList<>(itemCountPerSharding + 1);
            // 顺序向下分配
            for (int i = count * itemCountPerSharding; i < (count + 1) * itemCountPerSharding; i++) {
                shardingItems.add(i);
            }
            result.put(each, shardingItems);
            count++;
        }
        return result;
    }
}
