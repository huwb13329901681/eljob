package com.elastic.lambda;


public class Apple {

    private  String color;

    private Integer weigth;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getWeigth() {
        return weigth;
    }

    public void setWeigth(Integer weigth) {
        this.weigth = weigth;
    }

    public Apple(String color, Integer weigth) {
        this.color = color;
        this.weigth = weigth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Apple apple = (Apple) o;

        if (!color.equals(apple.color)) {
            return false;
        }
        return weigth.equals(apple.weigth);
    }

    @Override
    public int hashCode() {
        int result = color.hashCode();
        result = 31 * result + weigth.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Apple{" +
                "color='" + color + '\'' +
                ", weigth=" + weigth +
                '}';
    }
}
