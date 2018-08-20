package com.elastic.job.lambda;

@FunctionalInterface
public interface LambdaDemo<T> {

    void accept(T t);

    @Override
    String toString();
    boolean equals(Object o);

}
