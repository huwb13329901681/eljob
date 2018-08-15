package com.elastic.job.dataflowjob;


import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;

import java.util.List;

public class DataFlowDemo implements DataflowJob<Foo> {
    @Override
    public List<Foo> fetchData(ShardingContext shardingContext) {
        return null;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<Foo> data) {

    }
}
