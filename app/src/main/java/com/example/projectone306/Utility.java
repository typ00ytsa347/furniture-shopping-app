package com.example.projectone306;

import java.util.Locale;

public class Utility {

    public static String toDBCollectionName(String category) {
        if (!category.equals("Others")) {
            String collectionName = category.toLowerCase() + "s";
            return collectionName;
        } else {
            return category.toLowerCase();
        }
    }
}
