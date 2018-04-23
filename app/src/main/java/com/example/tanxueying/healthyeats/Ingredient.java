package com.example.tanxueying.healthyeats;

import com.example.tanxueying.healthyeats.Constant;

/**
 * Created by yaoxi on 2018/4/23.
 */

public class Ingredient {
    private int quantity;
    private String measureURI;
    private String foodURI;
    public Ingredient (String measureURI, String foodURI) {
        this.quantity = Constant.getQUANTITY();
        this.measureURI = measureURI;
        this.foodURI = foodURI;
    }
}
