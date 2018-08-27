package com.elastic.job.dataflowjob;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.elastic.job.enable.JobParameter;
import com.google.common.base.Splitter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @author hwb
 */
public abstract class AbstractDataFlowJob<T> implements DataflowJob<T> {

    private static final Logger log = LoggerFactory.getLogger(AbstractDataFlowJob.class);


    protected abstract List<T> fetchJobData(JobParameter var1);

    protected abstract void processJobData(List<T> var1);

    @Override
    public List<T> fetchData(ShardingContext shardingContext) {
        String jobName = shardingContext.getJobName();
        int shardingItem = shardingContext.getShardingItem();
        int shardingTotalCount = shardingContext.getShardingTotalCount();
        String taskId = shardingContext.getTaskId();
        String parameter = shardingContext.getJobParameter();
        Map<String, String> map = Splitter.on(",").withKeyValueSeparator("=").split(parameter);
        JobParameter jobTaskParameter = (new ModelMapper()).map(map, JobParameter.class);
        jobTaskParameter.setShardingItem(shardingItem).setShardingTotalCount(shardingTotalCount);
        log.info("扫描worker任务列表开始,jobName={}, shardingItem={}, shardingTotalCount={}, taskId={}", new Object[]{jobName, shardingItem, shardingTotalCount, taskId});
        long startTimestamp = System.currentTimeMillis();
        List<T> taskLst = fetchJobData(jobTaskParameter);
        int taskNo = taskLst != null ? taskLst.size() : 0;
        long endTimestamp = System.currentTimeMillis();
        log.info("扫描worker任务列表结束共计加载[{}]个任务, 耗时=[{}]", taskNo, endTimestamp - startTimestamp);
        return taskLst;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<T> workerTask) {
        log.info("任务[" + workerTask.get(0).getClass().getName() + "]开始执行...");
        long startTimestamp = System.currentTimeMillis();
        this.processJobData(workerTask);
        long endTimestamp = System.currentTimeMillis();
        log.info("任务[" + workerTask.get(0).getClass().getName() + "]执行完毕:耗时=[{}]", endTimestamp - startTimestamp);
    }
}