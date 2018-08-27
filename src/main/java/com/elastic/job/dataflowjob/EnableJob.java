package com.elastic.job.dataflowjob;

import org.springframework.context.annotation.Configuration;

import java.lang.annotation.*;

@Configuration
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableJob {

    /**
     * cron表达式，用于控制作业触发时间
     *
     * @return String string
     */
    String cron();

    /**
     * 作业分片总
     *
     * @return int int
     */
    int shardingTotalCount() default 1;

    /**
     * 分片序列号和个性化参数对照表.
     * 分片序列号和参数用等号分隔, 多个键值对用逗号分隔. 类似map.
     * 分片序列号从0开始, 不可大于或等于作业分片总数.
     * 如:
     * 0=a,1=b,2=c
     *
     * @return String string
     */
    String shardingItemParameters() default "";

    /**
     * 作业自定义参数.
     * 作业自定义参数，可通过传递该参数为作业调度的业务方法传参，用于实现带参数的作业
     * 例：每次获取的数据量、作业实例从数据库读取的主键等
     *
     * @return String string
     */
    String jobParameter() default "";
}
