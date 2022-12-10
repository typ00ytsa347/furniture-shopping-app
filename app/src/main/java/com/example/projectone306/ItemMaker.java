package com.example.projectone306;

import com.example.projectone306.items.Bed;
import com.example.projectone306.items.Carpet;
import com.example.projectone306.items.Chair;
import com.example.projectone306.items.Item;
import com.example.projectone306.items.Lamp;
import com.example.projectone306.items.Other;
import com.example.projectone306.items.Sofa;
import com.example.projectone306.items.Storage;
import com.example.projectone306.items.Table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemMaker {
    public static Item generateObject(String category) {
        switch (category) {
            case "chairs":
                Item chair = new Chair();
                return chair;
            case "beds":
                Item bed = new Bed();
                return bed;
            case "carpets":
                Item carpet = new Carpet();
                return carpet;
            case "lamps":
                Item lamp = new Lamp();
                return lamp;
            case "sofas":
                Item sofa = new Sofa();
                return sofa;
            case "storages":
                Item storage = new Storage();
                return storage;
            case "tables":
                Item table = new Table();
                return table;
            case "others":
                Item other = new Other();
                return other;
            default:
                return null;
        }
    }
}
