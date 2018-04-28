package com.example.tanxueying.healthyeats;

/**
 * Created by yaoxi on 2018/4/21.
 */

public class Constant {
    public final static String ID = "44d7dc52";
    public final static String KEY = "493c5c164c8457dfc1164704ca9b7a49";
    public final static String PARSER_URL = "https://api.edamam.com/api/food-database/parser?app_id=10804ad3&app_key=8ac0473de855b1492364d7150a0e9d64&ingr=";
    public final static String POST_URL = "https://api.edamam.com/api/food-database/nutrients?app_id=10804ad3&app_key=8ac0473de855b1492364d7150a0e9d64";
    //    private final static String PARSER = "parser?";
    private final static int QUANTITY = 1;

    public static String getId() {
        return ID;
    }
    public static String getKey() {
        return KEY;
    }
    public static int getQUANTITY() {
        return  QUANTITY;
    }
}
