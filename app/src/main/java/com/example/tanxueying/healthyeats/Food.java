package com.example.tanxueying.healthyeats;

/**
 * Created by yaoxi on 2018/4/21.
 */

public class Food {
    private String label;
    private String labelUrl;
    private int quantity;
    private String measure;
    private String measureUrl;
    private int cal;
    private int total_cal;

    public Food (String label, String labelUrl, String measure, String measureUrl, int cal) {
        this.label = label;
        this.labelUrl = labelUrl;
        this.measure = measure;
        this.measureUrl = measureUrl;
        this.quantity = Constant.getQUANTITY();
        this.cal = cal;
        this.total_cal = cal;
    }
    public Food (String label, String labelUrl, String measure, String measureUrl, int cal, int quantity) {
        this.label = label;
        this.labelUrl = labelUrl;
        this.measure = measure;
        this.measureUrl = measureUrl;
        this.cal = cal;
        setQuantity(quantity);
    }
    public void setQuantity (int quantity) {
        this.quantity = quantity;
        this.total_cal = cal * quantity;
    }
    public int getTotal_cal(){
        return total_cal;
    }
}

