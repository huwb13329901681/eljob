package com.elastic.job.single;

/**
 * @author Administrator
 */
public class Main {

    public static void main(String[] args) {

        SingleObject instance = SingleObject.getInstance();
        instance.showMessage();
    }
}
