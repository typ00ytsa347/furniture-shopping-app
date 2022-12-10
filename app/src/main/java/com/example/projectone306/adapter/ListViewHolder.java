package com.example.projectone306.adapter;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.example.projectone306.R;
import com.example.projectone306.adapter.ItemsAdapter;
import com.example.projectone306.adapter.BarViewHolder;
import com.example.projectone306.wishlistmethod.AddToList;

public class ListViewHolder extends BarViewHolder {
    Button heartButton;
    public ListViewHolder(@NonNull View itemView, ItemsAdapter.OnItemClickListener onItemClickListener) {
        super(itemView, onItemClickListener);
        heartButton = itemView.findViewById(R.id.heart_button);
        heartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO ADD TO CART
                new AddToList(nameProductTxt,"wishList");
            }
        });
    }
}
