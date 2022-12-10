package com.example.projectone306.adapter;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.example.projectone306.adapter.ItemsAdapter;
import com.example.projectone306.adapter.BarViewHolder;
import com.example.projectone306.wishlistmethod.AddToList;
import com.example.projectone306.wishlistmethod.DeleteItemInList;
import com.example.projectone306.wishlistmethod.GetListsOfNames;
import com.example.projectone306.R;

public class WishListViewHolder extends BarViewHolder {
    Button deleteButton;
    Button cartButton;

    public WishListViewHolder(@NonNull View itemView, ItemsAdapter.OnItemClickListener onItemClickListener) {
        super(itemView, onItemClickListener);

        cartButton = itemView.findViewById(R.id.cart_button);
        deleteButton = itemView.findViewById(R.id.delete_button_wishlist);
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
                new AddToList(nameProductTxt, "cart");
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DeleteItemInList(nameProductTxt,"wishList");
                //refresh Wishlist
                new GetListsOfNames("wishList");

            }
        });
    }


}
