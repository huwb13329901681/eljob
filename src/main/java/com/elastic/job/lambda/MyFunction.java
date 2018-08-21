package com.elastic.job.lambda;

@FunctionalInterface
public interface MyFunction<T,R> {

    R excurnt(T t);
}
