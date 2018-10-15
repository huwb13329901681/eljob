package com.elastic.job.factorypattern;

/**
 * @author Administrator
 */
public class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("Inside Circle::draw() method.");
    }
}
