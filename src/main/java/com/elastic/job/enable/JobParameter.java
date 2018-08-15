package com.elastic.job.enable;

import lombok.Data;

import java.io.Serializable;

@Data
public class JobParameter implements Serializable {
    private static final long serialVersionUID = -610797345091216847L;
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

    public JobParameter() {
    }
}