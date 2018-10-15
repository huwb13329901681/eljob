package com.elastic.job.factorypattern;

/**
 * @author Administrator
 */
public class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("Inside Rectangle::draw() method.");
    }
}
