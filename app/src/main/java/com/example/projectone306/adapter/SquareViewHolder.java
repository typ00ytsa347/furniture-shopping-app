package com.example.projectone306.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectone306.R;

class SquareViewHolder extends RecyclerView.ViewHolder implements ItemViewHolder {
    TextView nameBrandTxt, priceTxt, nameProductTxt;
    ImageView itemImage;
    Button itemButton;
    ItemsAdapter.OnItemClickListener onItemClickListener;

    public SquareViewHolder(@NonNull View itemView, ItemsAdapter.OnItemClickListener onItemClickListener){
        super(itemView);
        nameBrandTxt = itemView.findViewById(R.id.brand_name);
        priceTxt = itemView.findViewById(R.id.price);
        nameProductTxt = itemView.findViewById(R.id.item_name);
        itemImage = itemView.findViewById(R.id.photo);
        itemButton = itemView.findViewById(R.id.item);
        this.onItemClickListener = onItemClickListener;

        itemView.setOnClickListener(this);
        itemButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onItemClickListener.onItemClick(getAdapterPosition());
    }
}