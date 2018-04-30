package com.example.tanxueying.healthyeats;

import com.example.tanxueying.healthyeats.Constant;

/**
 * Created by yaoxi on 2018/4/23.
 */

public class Ingredient {
    private int quantity;
    private String measureURI;

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

    private String foodURI;
    public Ingredient (String measureURI, String foodURI) {
        this.quantity = 1;
        this.measureURI = measureURI;
        this.foodURI = foodURI;
    }
}
