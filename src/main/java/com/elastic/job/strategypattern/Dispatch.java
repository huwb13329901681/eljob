package com.elastic.job.strategypattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Administrator
 *
 * 具体的策略一调度单的code
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dispatch implements OrderCode {

    private String dCode;

    private String name;

    @Override
    public TfcWorkerTask getCodeNo(Object o) {
        Dispatch dispatch = (Dispatch) o;
        System.out.println("获取调度单code" + dispatch);
        TfcWorkerTask tfcWorkerTask = new TfcWorkerTask();
        tfcWorkerTask.setRefNo(dispatch.getDCode());
        return tfcWorkerTask;
    }
}
