package com.elastic.job.strategypattern;

/**
 * @author Administrator
 *
 * 定义一个抽象策略
 */
public interface OrderCode {

    /**
     *  获取code
     * @param o 传入的对象
     * @return worker
     */
    TfcWorkerTask getCodeNo(Object o);
}
