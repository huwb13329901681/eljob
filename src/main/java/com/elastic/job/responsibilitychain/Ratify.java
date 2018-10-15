package com.elastic.job.responsibilitychain;

/**
 * @author Administrator
 */
public interface Ratify {

    /**
     *  处理请求
     * @param chain chain
     * @return Result
     */
     Result deal(Chain chain);

    /**
     * 接口描述：对request和Result封装，用来转发
     */
    interface Chain {
        /**
         * 获取当前request
         * @return Request
         */
        Request request();

        /**
         * 转发request
         * @param request request
         * @return Result
         */
        Result proceed(Request request);
    }

}
