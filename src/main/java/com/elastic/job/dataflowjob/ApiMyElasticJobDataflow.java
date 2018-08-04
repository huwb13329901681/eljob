package com.elastic.job.dataflowjob;


import org.springframework.util.CollectionUtils;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author huwenbin
 *  Dataflow类型用于处理数据流 需实现Dataflow接口，该接口提供2个方法可覆盖，
 *  分别用于抓取(fetchData)和处理(processData)数据。
 *
 *  目前提供3种作业类型
 *  Simple：类型作业意为简单实现，未经任何封装的类型。需要继承AbstractSimpleElasticJob，该类只提供了一个方法用于覆盖，此方法将被定时执行。用于执行普通的定时任务，与Quartz原生接口相似，只是增加了弹性扩缩容和分片等功能。
 *  Dataflow ：类型用于处理数据流，它又提供2种作业类型，分别是ThroughputDataFlow和SequenceDataFlow。需要继承相应的抽象类。
 *           ThroughputDataFlow类型作业意为高吞吐的数据流作业。需要继承AbstractIndividualThroughputDataFlowElasticJob并可以指定返回值泛型，
 *          该类提供3个方法可覆盖，分别用于抓取数据，处理数据和指定是否流式处理数据。可以获取数据处理成功失败次数等辅助监控信息。如果流式处理数据，fetchData方法的返回值只有为null或长度为空时，作业才会停止执行，否则作业会
 *          一直运行下去；非流式处理数据则只会在每次作业执行过程中执行一次fetchData方法和processData方法，即完成本次作业。流式数据处理参照TbSchedule设计，适用于不间歇的数据处理。
 *          作业执行时会将fetchData的数据传递给processData处理，其中processData得到的数据是通过多线程（线程池大小可配）拆分的。如果采用流式作业处理方式，建议processData处理数据后更新其状态，避免fetchData再次抓取到，
 *          从而使得作业永远不会停止。processData的返回值用于表示数据是否处理成功，抛出异常或者返回false将会在统计信息中归入失败次数，返回true则归入成功次数。
 *  Script：类型用于处理脚本，可直接使用，无需编码。
 */
public class ApiMyElasticJobDataflow implements DataflowJob<Foo>{

    private FooRepository  fooRepository = FooRepositoryFactory.repository();

    /**
     * 抓取
     * @param context 分片上下文
     * @return List<Foo>
     */
    @Override
    public List<Foo> fetchData(ShardingContext context) {
        System.out.println("---------fetchData: "+context.getShardingParameter()+"-----------");
        List<Foo> result =  fooRepository.findTodoData(context.getShardingParameter(), 10);
        System.out.println(String.format("Item: %s | Time: %s | Thread: %s | %s | count: %d",
                context.getShardingItem(),
                new SimpleDateFormat("HH:mm:ss").format(new Date()),
                Thread.currentThread().getId(),
                "DATAFLOW FETCH",
                CollectionUtils.isEmpty(result)?0:result.size()));
        return result;

    }

    /**
     * 拉取
     * @param shardingContext shardingContext
     * @param data 数据源
     */
    @Override
    public void processData(ShardingContext shardingContext, List<Foo> data) {
        System.out.println("------------processData: "+shardingContext.getShardingParameter()+"----------");
        System.out.println(String.format("Item: %s | Time: %s | Thread: %s | %s | count: %d",
                shardingContext.getShardingItem(),
                new SimpleDateFormat("HH:mm:ss").format(new Date()),
                Thread.currentThread().getId(), "DATAFLOW PROCESS",
                CollectionUtils.isEmpty(data)?0:data.size()));
        for (Foo each : data) {
            fooRepository.setCompleted(each.getId());
        }

    }
}
