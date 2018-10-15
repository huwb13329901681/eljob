package com.elastic.job.strategypattern;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Administrator
 *
 * 具体的策略二运输单的code
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transport implements OrderCode {

    private String tCode;

    private String name;

    @Override
    public TfcWorkerTask getCodeNo(Object o) {
        Transport transport = (Transport) o;
        System.out.println("获取运输单code" + transport);
        TfcWorkerTask tfcWorkerTask = new TfcWorkerTask();
        tfcWorkerTask.setRefNo(transport.getTCode());
        return tfcWorkerTask;
    }

}
