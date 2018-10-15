package com.elastic.job.factorypattern;

/**
 * @author Administrator
 */
public class Square implements Shape {
    @Override
    public void draw() {
        System.out.println("Inside Square::draw() method.");
    }
}
