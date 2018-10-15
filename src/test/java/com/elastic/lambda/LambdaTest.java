package com.elastic.lambda;

import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author huwenbin
 */
public class LambdaTest {

    public static void main(String[] args) {

        List<Apple> appleList = Lists.newArrayList();
        Apple a1 = new Apple("red",10);
        Apple a2 = new Apple("blue",9);
        Apple a3 = new Apple("blue",9);

        appleList.add(a1);
        appleList.add(a2);
        appleList.add(a3);

        // 1.排序
        appleList.sort(Comparator.comparing(Apple::getWeigth));
        appleList.sort((Apple ap1, Apple ap2) ->ap1.getWeigth().compareTo(ap2.getWeigth()));

        //2.过滤red 返回一个集合
        appleList.stream().filter((Apple apple) -> ("red").equals(apple.getColor())).collect(Collectors.toList());
        List<Apple> red = appleList.stream().filter(apple -> apple.getWeigth() == 9 || Objects.equals(apple.getColor(), "red")).collect(Collectors.toList());
        System.out.println("过滤掉颜色是red和或者重量为9的数据："+red);

        //3.判断是否都是一个属性
        boolean b = appleList.stream().allMatch(apple -> ("red").equals(apple.getColor()));

        //4.查询第几个
        List<Apple> collect = appleList.stream().limit(1).collect(Collectors.toList());

        //5.for循环
        appleList.forEach(apple -> {
            System.out.println(apple.getWeigth());
        });

        //6.去掉list中重复的数据 如果是对象 需要重写equal和hashcode方法
        List<String> list = Lists.newArrayList("a","b","c","b");
        List<String> collect1 = list.stream().distinct().collect(Collectors.toList());

        long count = appleList.stream().distinct().count();
        appleList.stream().distinct().forEach(apple -> System.out.println(apple.getWeigth()+","+apple.getColor()));

        //7.跳过元素返回一个扔掉前n个元素
        List<Apple> collect2 = appleList.stream().skip(1).collect(Collectors.toList());

        //8.将一个类型中的元素的值修改后输出
        List<String> strings = appleList.stream().map(apple -> apple.getColor() + "yes").collect(Collectors.toList());
        System.out.println("给颜色添加yes"+strings);
        List<String> collect4 = appleList.stream().map(Apple::getColor).distinct().collect(Collectors.toList());
        System.out.println("返回流中所有的coloeq去掉重复的"+collect4);
        // for循环是没有返回值的。
        List<Banana> bs = Lists.newArrayList();
        appleList.forEach(apple -> {
            Banana banana = new Banana();
            BeanUtils.copyProperties(apple,banana);
            banana.setType("1");
            bs.add(banana);
        });
        System.out.println("使用for循环："+bs);
        //stream.map修改老数据，返回一个新的数据，有返回值
        List<Banana> bananas = appleList.stream().map(apple -> {
            Banana banana = new Banana();
            BeanUtils.copyProperties(apple, banana);
            banana.setType("1");
            return banana;
        }).collect(Collectors.toList());
        System.out.println("使用stream流" + bananas);

        Optional<Apple> collect5 = appleList.stream().max((apple1, apple2) -> apple1.getWeigth() - apple2.getWeigth());
        Optional<Apple> collect3 = appleList.stream().collect(Collectors.maxBy((apple1, apple2) -> apple1.getWeigth() - apple2.getWeigth()));
        appleList.stream().max(Comparator.comparingInt(Apple::getWeigth));
        System.out.println("最重的水果："+collect3);

        //9.返回流中的最大值
        appleList.stream().max(Comparator.comparing(Apple::getWeigth));
        Optional<Apple> max = appleList.stream().max((appl1, appl2) -> appl1.getWeigth().compareTo(appl2.getWeigth()));
        System.out.println("返回最大值："+max);

        //10.filter和map的联合使用 先查询所有blue的数据。然后找出重量
        List<Integer> integers = appleList.stream()
                .filter((Apple apple) -> ("blue").equals(apple.getColor()))
                .map(Apple::getWeigth)
                .collect(Collectors.toList());
        System.out.println("使用filter和map联合查询："+integers);

        // 比较的是地址
        boolean b1 = a2 == a3;

        // 比较的是内容 如果Apple 没有从新equals方法则也是直接比较的地址，如果重写了比较的是内容
        boolean b2 = a2.equals(a3);
        System.out.println("b1 = " + b1 + "b2 = "+ b2);

        //11.排序不区分大小写
        List<String> lists = Lists.newArrayList("a","c","D","B");
        lists.sort((s1,s2) ->s1.compareToIgnoreCase(s2));
        lists.sort(String::compareToIgnoreCase);
        System.out.println("排序"+lists);

    }
}
