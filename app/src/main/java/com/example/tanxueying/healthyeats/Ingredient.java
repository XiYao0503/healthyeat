package com.example.tanxueying.healthyeats;

import com.example.tanxueying.healthyeats.Constant;

/**
 * Created by yaoxi on 2018/4/23.
 */

public class Ingredient {
    private int quantity;
    private String measureURI;
    private String foodURI;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMeasureURI() {
        return measureURI;
    }

    public void setMeasureURI(String measureURI) {
        this.measureURI = measureURI;
    }

    public String getFoodURI() {
        return foodURI;
    }

    public void setFoodURI(String foodURI) {
        this.foodURI = foodURI;
    }

    public Ingredient (String measureURI, String foodURI) {
        this.quantity = Constant.getQUANTITY();

        this.measureURI = measureURI;
        this.foodURI = foodURI;
    }
}
