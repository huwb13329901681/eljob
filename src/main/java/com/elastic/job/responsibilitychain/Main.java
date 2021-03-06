package com.elastic.job.responsibilitychain;

/**
 * @author Administrator
 */
public class Main {

    public static void main(String[] args) {

        Request request = new Request.Builder().setName("张三").setDays(1)
                .setReason("事假").build();
        ChainOfResponsibilityClient client = new ChainOfResponsibilityClient();
        Result result = client.execute(request);

        System.out.println("结果：" + result.toString());

    }
}
