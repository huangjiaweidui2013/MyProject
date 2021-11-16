package com.example.springbootsecurity.demo.entity;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.demo.entity
 * @className: Dish
 * @author: HuangLang
 * @description: 食物
 * @date: 2021-11-16 下午 3:44
 */
public class Dish {
    private final String name;
    private final boolean vegetarian;
    private final int calories;
    private final Type type;

    public Dish(String name, boolean vegetarian, int calories, Type type) {
        this.name = name;
        this.vegetarian = vegetarian;
        this.calories = calories;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public int getCalories() {
        return calories;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return name;
    }

    public enum Type {MEAT, FISH, OTHER;}
}
