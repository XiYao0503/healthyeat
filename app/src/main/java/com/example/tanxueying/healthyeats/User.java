package com.example.tanxueying.healthyeats;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String username;
    private String gender;
    private String age;
    private String height;
    private String weight;
    private String exercise;
    private String goal_cal;
    private List<Food> foodList;
    private String net_cal;
    private String total_cal;
    public User() {

    }

    public User(String username, String gender, String age, String height, String weight, String exercise) {
        this.username = username;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.exercise = exercise;
        this.foodList =  new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public String getGoal_cal() {
        return goal_cal;
    }


    public void setCal() {
        Double h = Double.parseDouble(this.height) * 2.54;
        Double w = Double.parseDouble(this.weight) / 2.2;
        Integer a = Integer.parseInt(this.age);
        Integer e = Integer.parseInt(this.exercise);
        Double c = new Double(0);
        if (this.gender.equals("Male")) {
            c = 66.47+ (13.75 * w) + (5.0 * h) - (6.75 * a) + e;
        } else {
            c = 665.09 + (9.56 * w) + (1.84 * h) - (4.67 * a) + e;
        }
        this.goal_cal = String.valueOf(c.intValue());
        this.net_cal = goal_cal;
        this.total_cal = 0 + "";
    }
    public void addFood(Food food) {
        // update food list
        // update net
        foodList.add(food);
        total_cal = String.valueOf(Integer.parseInt(total_cal)+food.getTotal_cal());
        net_cal = String.valueOf(Integer.parseInt(net_cal)-food.getTotal_cal());
    }

    public String getNet() {
        return this.net_cal;
    }

    public void setNet(String net) {
        this.net_cal = net;
    }

    public String getTotal() {
        return this.total_cal;
    }

    public void setTotal(String total_cal) {
        this.total_cal = total_cal;
    }

    public List<Food> getFood() {
        return this.foodList;
    }
    public void setFood(List<Food> foodList) {
        this.foodList = foodList;
    }
}
