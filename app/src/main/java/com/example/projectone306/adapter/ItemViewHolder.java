package com.example.projectone306.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public interface ItemViewHolder extends View.OnClickListener {
    TextView nameBrandTxt = null;
    TextView priceTxt = null;
    TextView nameProductTxt = null;
    ImageView itemImage = null;
    Button itemButton = null;
    ItemsAdapter.OnItemClickListener onItemClickListener = null;

}
