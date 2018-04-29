package com.example.tanxueying.healthyeats;

/**
 * Created by yaoxi on 2018/4/21.
 */

public class Food {
    private String label;
    private String labelUrl;
    private float quantity;


    private float yield;
    private String measure;
    private String measureUrl;
    private int cal;
    private int total_cal;


    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getYield() {
        return yield;
    }

    public void setYield(float yield) {
        this.yield = yield;
    }


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabelUrl() {
        return labelUrl;
    }

    public void setLabelUrl(String labelUrl) {
        this.labelUrl = labelUrl;
    }


    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getMeasureUrl() {
        return measureUrl;
    }

    public void setMeasureUrl(String measureUrl) {
        this.measureUrl = measureUrl;
    }

    public int getCal() {
        return cal;
    }

    public void setCal(int cal) {
        this.cal = cal;
    }

    public int getTotal_cal() {
        return total_cal;
    }

    public void setTotal_cal(int total_cal) {
        this.total_cal = total_cal;
    }



    public Food (String label, String labelUrl, String measure, String measureUrl) {
        this.label = label;
        this.labelUrl = labelUrl;
        this.measure = measure;
        this.measureUrl = measureUrl;
        this.quantity = Constant.getQUANTITY();
        this.yield = Constant.getQUANTITY();
        this.cal = 0;
        this.total_cal = 0;
    }


}

