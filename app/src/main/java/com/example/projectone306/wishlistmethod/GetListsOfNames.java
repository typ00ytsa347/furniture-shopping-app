package com.example.projectone306.wishlistmethod;

import android.widget.Toast;

import com.example.projectone306.adapter.ItemsAdapter;
import com.example.projectone306.ui.login.AccountInfoFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Map;

public class GetListsOfNames {
    private static ArrayList<String> wishListItemNames = new ArrayList<>();
    private static ArrayList<String> CartItemNames = new ArrayList<>();
    private String userID;
    private String listType;

    public GetListsOfNames(String listName){
        if(listName.equalsIgnoreCase("wishlist")){
            listType = "wishList";
        }
        else if (listName.equalsIgnoreCase("cart")){
            listType = "cart";
        }
        else{
            listName = null;
            Toast.makeText(ItemsAdapter.getContext(), "listName not expected", Toast.LENGTH_SHORT).show();
        }
        userID = AccountInfoFragment.getCurrentUserId();
        if (userID == null) {

        } else {
            DatabaseReference wishListRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userID).child(listType);

            if (wishListRef == null) {
                ArrayList<String> defaultChair = new ArrayList<>();
                defaultChair.add("Default Chair");
                wishListRef.updateChildren((Map<String, Object>) defaultChair);
            } else {
                wishListRef.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                //Get map of users in datasnapshot
                                collectItemNames((Map<String, Object>) dataSnapshot.getValue(),listType);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                //handle databaseError
                            }
                        });
            }
        }
    };


    public void collectItemNames(Map<String, Object> items, String listName) {
        //iterate through each item, ignoring their UID
        if (items != null) {
        for (Map.Entry<String, Object> entry : items.entrySet()){

            //Get item map
                Map singleItem = (Map) entry.getValue();
                //Decide which array should it be add on
                 if(listName.equalsIgnoreCase("wishList")){
                     if (wishListItemNames.isEmpty()) {
                         wishListItemNames.add((String) singleItem.get("itemName"));
                     } else if (!(wishListItemNames.contains(singleItem.get("itemName")))) {
                         wishListItemNames.add((String) singleItem.get("itemName"));
                     }
                 }
                 else{
                     if (CartItemNames.isEmpty()) {
                         CartItemNames.add((String) singleItem.get("itemName"));
                     } else if (!(CartItemNames.contains(singleItem.get("itemName")))) {
                         CartItemNames.add((String) singleItem.get("itemName"));
                     }
                 }
            }

        }
    }
    public static ArrayList getWishListItemNames(){
        return wishListItemNames;
    }

    public static ArrayList getCartItemNames(){
        return CartItemNames;
    }


}
