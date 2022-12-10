package com.example.projectone306.wishlistmethod;

import static com.example.projectone306.adapter.ItemViewHolder.nameProductTxt;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.projectone306.adapter.ItemsAdapter;
import com.example.projectone306.ui.login.AccountInfoFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddToList {
    public AddToList(TextView nameProductTxt, String listName) {
        String saveCurrentDate, saveCurrentTime;
        String userID;
        Calendar carForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("yyyy MMM dd");
        saveCurrentDate = currentDate.format(carForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(carForDate.getTime());

        DatabaseReference wishListRef = FirebaseDatabase.getInstance().getReference().child("Users");

        final HashMap<String, Object> wishListMap = new HashMap<>();
        wishListMap.put("itemName",nameProductTxt.getText().toString());
        wishListMap.put("date",saveCurrentDate);
        wishListMap.put("time",saveCurrentTime);

        userID = AccountInfoFragment.getCurrentUserId();
        if(userID == null){
            Toast.makeText(ItemsAdapter.getContext(), "Please log in", Toast.LENGTH_SHORT).show();
        }
        else {
            wishListRef.child(userID).child(listName).child(nameProductTxt.getText().toString()).updateChildren(wishListMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(ItemsAdapter.getContext(), "Item is added to "+ listName, Toast.LENGTH_SHORT).show();
                        //refresh Wishlist
                        new GetListsOfNames(listName);
                    }
                }

            });
        }
    }
}
