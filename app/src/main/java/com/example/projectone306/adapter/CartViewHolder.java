package com.example.projectone306.adapter;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.example.projectone306.R;
import com.example.projectone306.adapter.ItemsAdapter;
import com.example.projectone306.adapter.BarViewHolder;
import com.example.projectone306.wishlistmethod.DeleteItemInList;
import com.example.projectone306.wishlistmethod.GetListsOfNames;

public class CartViewHolder extends BarViewHolder {
    Button deleteButton;

    public CartViewHolder(@NonNull View itemView, ItemsAdapter.OnItemClickListener onItemClickListener) {
        super(itemView, onItemClickListener);

        deleteButton = itemView.findViewById(R.id.delete_button_cart);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DeleteItemInList(nameProductTxt,"cart");
                //refresh Wishlist
                new GetListsOfNames("cart");

            }
        });
    }


}

