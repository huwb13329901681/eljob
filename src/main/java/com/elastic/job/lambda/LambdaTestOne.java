package com.elastic.job.lambda;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author huwenbin
 */
public class LambdaTestOne {

    public static void main(String[] args) {
        List<Apple> appleList = Lists.newArrayList();
        Apple a1 = new Apple("red",10);
        Apple a2 = new Apple("blue",9);
        Apple a3 = new Apple("blue",9);

        appleList.add(a1);
        appleList.add(a2);
        appleList.add(a3);
    }
}
