package com.example.projectone306;

import com.example.projectone306.items.Item;

import java.util.ArrayList;
import java.util.Locale;

public class Searcher {
    private String searchedKeyword, searchedBrandName;
    private float priceRangeLower, priceRangeUpper;

    public Searcher(String searchedKeyword) {
        this.searchedKeyword = searchedKeyword;
    }

    public Searcher(String searchedKeyword, String searchedBrandName, float priceRangeLower, float priceRangeUpper) {
        this.searchedKeyword = searchedKeyword;
        this.searchedBrandName = searchedBrandName;
        this.priceRangeLower = priceRangeLower;
        this.priceRangeUpper = priceRangeUpper;
    }

    /**
     * Search function for MainActivity
     * Retrieves keyword matching/containing item name/brand name objects
     * @param allItems
     * @return
     */
    public ArrayList<Item> search(ArrayList<Item> allItems) {
        ArrayList<Item> searchResult = new ArrayList<>();
        if (searchedKeyword == null) {
            searchedKeyword = "";
        } else {
            searchedKeyword.toLowerCase();
        }

        for (Item item : allItems) {

            if ((item.getItemName().toLowerCase().contains(searchedKeyword)) ||
                (item.getBrandName().toLowerCase().contains(searchedKeyword))) {
                searchResult.add(item);
            }
        }

        return searchResult;
    }

    /**
     * Advanced search
     * Retrieves keyword matching/containing and brand/category/price matching items
     * @param allItems
     * @return
     */
    public ArrayList<Item> searchAdvanced(ArrayList<Item> allItems) {
        ArrayList<Item> searchResult = new ArrayList<>();
        searchedKeyword.toLowerCase();

        for (Item item : allItems) {
            if ((searchedBrandName.equals(item.getBrandName())) &&
                (priceRangeLower <= item.getPrice()) &&
                (priceRangeUpper >= item.getPrice()) &&
                (item.getItemName().toLowerCase().contains(searchedKeyword))) {

                searchResult.add(item);
            }
        }

        return searchResult;
    }
}
