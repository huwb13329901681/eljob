package com.elastic.job.dataflowjob;

import lombok.Data;

import java.io.Serializable;

/**
 * @author huwenbin
 */
@Data
public class JobParameter implements Serializable {
    private static final long serialVersionUID = 4960733253342570720L;
    private int fetchNum;
    int shardingItem;
    int shardingTotalCount;

    public JobParameter setShardingItem(int shardingItem) {
        this.shardingItem = shardingItem;
        return this;
    }

    public JobParameter setShardingTotalCount(int shardingTotalCount) {
        this.shardingTotalCount = shardingTotalCount;
        return this;
    }

    public JobParameter setFetchNum(int fetchNum) {
        this.fetchNum = fetchNum;
        return this;
    }

    public JobParameter() {
    }
}