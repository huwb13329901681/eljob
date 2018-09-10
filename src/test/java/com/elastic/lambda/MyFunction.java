package com.elastic.lambda;

@FunctionalInterface
public interface MyFunction<T,R> {

    R excurnt(T t);
}
