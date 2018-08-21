package com.elastic.job.lambda;

import lombok.Data;

/**
 * @author huwenbin
 */
@Data
public class Person {

    private String name;
    private Integer age;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
