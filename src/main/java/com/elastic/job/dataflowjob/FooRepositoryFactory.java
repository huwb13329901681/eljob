package com.elastic.job.dataflowjob;

/**
 * @author huwenbin
 * 具体处理工厂类
 */
public class FooRepositoryFactory {

    private static FooRepository fooRepository  = new FooRepository();

    public static FooRepository repository() {
        return fooRepository ;
    }
}
