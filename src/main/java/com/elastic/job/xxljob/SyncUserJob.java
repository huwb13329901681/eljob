package com.elastic.job.xxljob;


import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;
import org.springframework.stereotype.Component;

/**
 * @author huwb
 *
 * @JobHander 唯一标识一个任务。调度中心会查找这个唯一的名称来启动执行此类的execute方法。
 */
@Component
@JobHander(value="SyncUserJob")
public class SyncUserJob extends IJobHandler {

    @Override
    public ReturnT<String> execute(String... strings) throws Exception {
        System.out.println("启动成功......");
        return ReturnT.SUCCESS;
    }
}
