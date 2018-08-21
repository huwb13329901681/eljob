package com.elastic.job.lambda;

@FunctionalInterface
public interface ConversionFunction<T,R> {

    R conversion(T t);
}
