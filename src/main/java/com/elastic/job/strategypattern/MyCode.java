package com.elastic.job.strategypattern;


import lombok.Data;

/**
 * @author Administrator
 *
 * 定义上下文，选择方式
 */
@Data
class MyCode<T> {

    private OrderCode orderCode;

    private Object object;

    MyCode(T t, Object object){
        this.orderCode = (OrderCode) t;
        this.object = object;
    }

    /**
     * 选择类型，是调度单还是运输单
     * @return worker
     */
    TfcWorkerTask getCodeNo(){
        return this.orderCode.getCodeNo(object);
    }
}
