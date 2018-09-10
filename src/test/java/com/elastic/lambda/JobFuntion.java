package com.elastic.lambda;


/**
 * @author huwenbin
 * 自定义函数式接口，只能有一个抽象方法
 */
@FunctionalInterface
public interface JobFuntion<T,R> {

    R handler(T t1, T t2);
}
