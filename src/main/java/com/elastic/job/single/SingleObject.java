package com.elastic.job.single;

/**
 * @author Administrator
 */
public class SingleObject {

    private static SingleObject singleObject = new SingleObject();

    private SingleObject(){}

    public static SingleObject getInstance(){
        return singleObject;
    }

    public void showMessage() {
        System.out.println("hello word");
    }
}
