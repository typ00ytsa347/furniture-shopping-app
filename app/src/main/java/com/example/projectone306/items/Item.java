package com.example.projectone306.items;

/**
 * Furniture item super class
 */
import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;

public class Item implements Parcelable {
    private String brandName, itemName, imageURL, imageURLTwo, imageURLThree, description, recommendation, specification;
    public int viewCount;
    private float price;

    public Item() {}

    public Item(String brandName, String itemName, String imageURL, String imageURLTwo, String imageURLThree,
                String description, String recommendation, String specification, float price, int viewCount) {
        this.brandName = brandName;
        this.itemName = itemName;
        this.imageURL = imageURL;
        this.imageURLTwo = imageURLTwo;
        this.imageURLThree = imageURLThree;
        this.description = description;
        this.recommendation = recommendation;
        this.specification = specification;
        this.price = price;
        this.viewCount = viewCount;
    }

    protected Item(Parcel in) {
        brandName = in.readString();
        itemName = in.readString();
        imageURL = in.readString();
        imageURLTwo = in.readString();
        imageURLThree = in.readString();
        description = in.readString();
        recommendation = in.readString();
        specification = in.readString();
        price = in.readFloat();
        viewCount = in.readInt();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public String getBrandName() {
        return this.brandName;
    }
    public void setBrandName(String brandName){
        this.brandName = brandName;
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImageURLTwo() {
        return imageURLTwo;
    }

    public void setImageURLTwo(String imageURLTwo) {
        this.imageURLTwo = imageURLTwo;
    }

    public String getImageURLThree() {
        return imageURLThree;
    }

    public void setImageURLThree(String imageURLThree) {
        this.imageURLThree = imageURLThree;
    }

    public String getImageURL() {
        return this.imageURL;
    }

    public void setImageURL(String url) {
        this.imageURL = url;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(brandName);
        parcel.writeString(itemName);
        parcel.writeString(imageURL);
        parcel.writeString(imageURLTwo);
        parcel.writeString(imageURLThree);
        parcel.writeString(description);
        parcel.writeString(recommendation);
        parcel.writeString(specification);
        parcel.writeFloat(price);
        parcel.writeInt(viewCount);
    }
}
