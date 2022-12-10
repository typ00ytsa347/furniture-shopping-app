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

public class DeleteItemInList {
    String userID;
    public DeleteItemInList(TextView nameProductTxt, String listName) {
        userID = AccountInfoFragment.getCurrentUserId();
        if (userID == null) {
            Toast.makeText(ItemsAdapter.getContext(), "Please log in", Toast.LENGTH_SHORT).show();
        } else {
            DatabaseReference wishListRef = FirebaseDatabase.getInstance().getReference().child("Users");
            wishListRef.child(userID).child(listName).child(nameProductTxt.getText().toString()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(ItemsAdapter.getContext(), "Item is removed from " +listName, Toast.LENGTH_SHORT).show();
                        Toast.makeText(ItemsAdapter.getContext(), "Restart App to update the" + listName, Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }
    }
}
