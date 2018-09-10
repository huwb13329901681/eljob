package com.elastic.lambda;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * @author huwenbin
 */
public class Java8CharacteristicTest {

    /**
     * 利用自定义函数编程计算x + y
     * @param x 第一个参数值x
     * @param y 第二个参数值y
     * @param myFunction 自定义的函数接口
     * @return x + y
     */
    private Long getAdd(Long x, Long y, JobFuntion<Long, Long> myFunction) {
        Long handler = myFunction.handler(x, y);
        return handler;
    }

    @Test
    public void testGetAdd() {
        Long result = getAdd(1L, 2L, (x, y) -> x + y);
        System.out.println("计算得出的结果为：" + result);
    }

    /**
     * 利用自定义函数编程将int类型转化为Spring类型
     * @param x 第一个int数字
     * @param y 第二个int数字
     * @param myFunction 自定义的函数接口
     * @return 将int转化为Spring的结果
     */
    private String getIntToString(int x, int y, JobFuntion<Integer, String> myFunction) {
        return myFunction.handler(x, y);
    }

    @Test
    public void testGetIntToString() {
        String result = getIntToString(18, 20, (x, y) -> {
            return "小明的年龄在".concat(String.valueOf(x)).concat("岁到").concat(String.valueOf(y)).concat("岁之间");
        });


        System.out.println(result);
    }

    /**
     * 利用自定义函数编程通过姓名和年龄过滤Person列表
     * @param x 参数x
     * @param y 参数y
     * @param myFunction 自定义的函数接口
     * @return 过滤后的Person列表
     */
    public List<Person> getPersonsByNameAndAge(String x, String y, JobFuntion<String, List<Person>> myFunction){
        return myFunction.handler(x, y);
    }

    @Test
    public void testGetPersonsByNameAndAge() {
        List<Person> list = Arrays.asList(
                new Person("刘亚壮", 20),
                new Person("张三", 26),
                new Person("李四", 27),
                new Person("小明", 18),
                new Person("小李", 30)
        );

        String name = "小明";
        String age = "18";
        List<Person> persons = getPersonsByNameAndAge(name, age, (x, y) -> {
            List<Person> subPersons = list.stream().filter(p -> p.getAge() >= Integer.parseInt(y) && p.getName().equals(x)).collect(Collectors.toList());
            return subPersons;
        });
        persons.stream().forEach(System.out::println);
    }


    /**
     * 利用自定义函数编程将int类型转化为Spring类型
     * @param apple 参数apple
     * @param myFunction 自定义函数接口
     * @return 将apple转换成String
     */
    private String getColor(Apple apple, Function<Apple,String> myFunction) {
        String color = myFunction.apply(apple);
        return color;
    }

    @Test
    public void testGetColor(){
        Apple a1 = new Apple("red",10);
        String color = getColor(a1, a -> a.getColor());
        System.out.println(color);
    }
}
