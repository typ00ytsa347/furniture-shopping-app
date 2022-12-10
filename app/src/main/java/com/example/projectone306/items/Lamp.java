package com.example.projectone306.items;

import java.math.BigDecimal;

public class Lamp extends Item{
    public Lamp() {
        super();
    }

    public Lamp(String brandName, String itemName, String imageURL, String imageURLTwo, String imageURLThree,
                String description, String recommendation, String specification, float price, int viewCount) {
        super(brandName, itemName, imageURL, imageURLTwo, imageURLThree, description, recommendation,
                specification, price, viewCount);
    }
}
