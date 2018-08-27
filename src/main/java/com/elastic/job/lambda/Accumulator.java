package com.elastic.job.lambda;

public class Accumulator {

    public long total = 0;

    public void add(long value) {
        total += value;
    }
}
