package com.example.tanxueying.healthyeats;

import java.text.DecimalFormat;

/**
 * Created by yaoxi on 2018/4/21.
 */

public class Food {
    private String label;
    private String labelUrl;
    private String measure;
    private String measureUrl;
    private String kcal;
    private String total_kcal;
    private String quantity;
    private String yield;

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

    public String getKcal() {
        return kcal;
    }

    public void setKcal(String kcal) {
        this.kcal = kcal;
    }

    public String getTotal_kcal() {
        return total_kcal;
    }

    public void setTotal_kcal(String total_kcal) {
        this.total_kcal = total_kcal;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getYield() {
        return yield;
    }

    public void setYield(String yield) {
        this.yield = yield;
    }

    public Food() {}

    public Food (String label, String labelUrl, String measure, String measureUrl) {
        this.label = label;
        this.labelUrl = labelUrl;
        this.measure = measure;
        this.measureUrl = measureUrl;
        this.quantity = Constant.getQUANTITY();

    }


}

