package com.example.tanxueying.healthyeats;

/**
 * Created by yaoxi on 2018/4/23.
 */

public class Food_nutrition {
    private int yield;
    private Ingredient[] ingredients;

    public Food_nutrition(int yield, Ingredient ingredient) {

        this.yield = yield;
        ingredients = new Ingredient[]{ingredient};
    }
    public int getYield() {
        return yield;
    }


}
