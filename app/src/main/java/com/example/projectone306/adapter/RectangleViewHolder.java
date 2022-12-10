package com.example.projectone306.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectone306.R;
import com.example.projectone306.wishlistmethod.AddToList;

class RectangleViewHolder extends RecyclerView.ViewHolder implements ItemViewHolder {
    TextView nameBrandTxt, priceTxt, nameProductTxt;
    ImageView itemImage;
    Button itemButton, heartButton;
    ItemsAdapter.OnItemClickListener onItemClickListener;

    public RectangleViewHolder(@NonNull View itemView, ItemsAdapter.OnItemClickListener onItemClickListener){
        super(itemView);
        nameBrandTxt = itemView.findViewById(R.id.brand_name);
        priceTxt = itemView.findViewById(R.id.price);
        nameProductTxt = itemView.findViewById(R.id.item_name);
        itemImage = itemView.findViewById(R.id.photo);
        itemButton = itemView.findViewById(R.id.item);
        heartButton = itemView.findViewById(R.id.heart_button);
        this.onItemClickListener = onItemClickListener;

        itemView.setOnClickListener(this);
        itemButton.setOnClickListener(this);
        heartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO ADD TO CART
                new AddToList(nameProductTxt,"wishList");
            }
        });
    }

    @Override
    public void onClick(View view) {
        onItemClickListener.onItemClick(getAdapterPosition());
    }
}