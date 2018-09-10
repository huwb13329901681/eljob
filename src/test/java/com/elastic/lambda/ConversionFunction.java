package com.elastic.lambda;

@FunctionalInterface
public interface ConversionFunction<T,R> {

    R conversion(T t);
}
