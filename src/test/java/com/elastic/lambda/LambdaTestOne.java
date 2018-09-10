package com.elastic.lambda;

import com.google.common.collect.Lists;

import java.util.Comparator;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author huwenbin
 */
public class LambdaTestOne {

    public static void main(String[] args) {
        List<Apple> appleList = Lists.newArrayList();
        Apple a1 = new Apple("red",10);
        Apple a2 = new Apple("blue",9);
        Apple a3 = new Apple("blue",19);

        appleList.add(a1);
        appleList.add(a2);
        appleList.add(a3);

        //1.方法引用::
        Function<String,Integer> isr1 = (String sr) ->Integer.parseInt(sr);
        Function<String,Integer> isr2 =Integer::parseInt;

        BiPredicate<List<String>, String> contains1 =  (list, element) -> list.contains(element);
        BiPredicate<List<String>, String> contains2 =List::contains;
        appleList.stream().filter(apple -> ("red").equals(apple.getColor())).map(Apple::getWeigth).collect(Collectors.toList());
        appleList.stream().filter(apple -> ("blue").equals(apple.getColor())).map(apple -> apple.getWeigth()).collect(Collectors.toList());

        //2.按重量倒叙，在重量一样的情况下按颜色排序(默认从小到大)
        appleList.sort(Comparator.comparing(Apple::getWeigth).reversed().thenComparing(Apple::getColor));

        //3.先判断是blue然后在判断大于10的
        appleList.parallelStream().filter(apple -> apple.getColor().equals("blue")).filter(apple -> apple.getWeigth() > 10).collect(Collectors.toList());
        appleList.stream().filter(apple -> apple.getColor().equals("blue") && apple.getWeigth() > 10).collect(Collectors.toList());

        //4.
    }

}
