package com.elastic.job.lambda;

import java.io.Serializable;
import java.math.BigDecimal;

public class Banana implements Serializable{

    private  String color;

    private BigDecimal weigth;

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BigDecimal getWeigth() {
        return weigth;
    }

    public void setWeigth(BigDecimal weigth) {
        this.weigth = weigth;
    }

    public Banana(String color, BigDecimal weigth) {
        this.color = color;
        this.weigth = weigth;
    }

    public Banana() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Banana banana = (Banana) o;

        if (!color.equals(banana.color)) return false;
        if (!weigth.equals(banana.weigth)) return false;
        return type.equals(banana.type);
    }

    @Override
    public int hashCode() {
        int result = color.hashCode();
        result = 31 * result + weigth.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Banana{" +
                "color='" + color + '\'' +
                ", weigth=" + weigth +
                ", type='" + type + '\'' +
                '}';
    }
}
