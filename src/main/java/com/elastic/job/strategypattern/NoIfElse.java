package com.elastic.job.strategypattern;

import com.alibaba.fastjson.JSON;

/**
 * @author Administrator
 *
 * 策略模式
 * 1.定义一个抽象策略
 * 2.定义具体的的类型---调度单/运输单
 * 3.定义上下文选择方式
 * 4.主方法，选择某种类型
 *
 * 生活中的案例
 * 比如说我要出行旅游，那么出行方式有--飞机、自驾游、火车等，这几种方式就是策略。
 * 再比如：某大型商场搞活动--满 100 元送杯子，满 300 减 50 ，满 1000 元抽奖
 * 「一等将彩色电视机」，这种活动也是策略
 */
public class NoIfElse {
    public static void main(String[] args) {
        Dispatch dispatch = new Dispatch("DP123","张三");
        Transport transport = new Transport("SO123","李四");
        // 选择类型
        Object obj = transport;
        TfcWorkerTask workerTask = buildWorkerTask(obj);
        System.out.println("返回的woker ：" + workerTask);
    }

    private static TfcWorkerTask buildWorkerTask(Object o){
        // 传入一个持有OrderCode的引用
        String date = JSON.toJSONString(o);
        MyCode myCode = new MyCode(o,o);
        TfcWorkerTask workerTask = myCode.getCodeNo();
        workerTask.setTaskData(date);
        return workerTask;
    }
}
