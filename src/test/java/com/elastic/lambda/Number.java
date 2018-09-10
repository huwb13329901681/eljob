package com.elastic.lambda;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 */
@Data
public class Number implements Serializable {

    private String num;

    private List<String> ref;
}
