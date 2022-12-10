package com.example.projectone306.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectone306.R;
import com.example.projectone306.activity.ListActivity;
import com.example.projectone306.items.Item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ItemsAdapter extends RecyclerView.Adapter{
    private static Context context;
    private static int TYPE_SQUARE = 1;
    private static int TYPE_LIST = 2;
    private static int TYPE_WISHLIST = 3;
    private static int TYPE_CART = 4;
    private static int TYPE_RECTANGLE = 5;

    private ArrayList<Item> itemsList;
    private OnItemClickListener mOnItemClickListener;

    public ItemsAdapter(Context context, ArrayList<Item> itemsList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.itemsList = itemsList;
        this.mOnItemClickListener = onItemClickListener;
        notifyDataSetChanged();
    }

    public static Context getContext() {
        return context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        if(viewType == TYPE_SQUARE) {
            itemView = LayoutInflater.from(context).inflate(R.layout.list_items_square, parent, false);
            return new SquareViewHolder(itemView, mOnItemClickListener);
        }
        else if(viewType == TYPE_RECTANGLE){
            itemView = LayoutInflater.from(context).inflate(R.layout.list_items_rectangle, parent, false);
            return new RectangleViewHolder(itemView, mOnItemClickListener);
        }
        else if(viewType == TYPE_WISHLIST){
            itemView = LayoutInflater.from(context).inflate(R.layout.list_items_wishlist, parent, false);
            return new WishListViewHolder(itemView, mOnItemClickListener);
        }
        else if(viewType == TYPE_CART){
            itemView = LayoutInflater.from(context).inflate(R.layout.list_items_cart, parent, false);
            return new CartViewHolder(itemView, mOnItemClickListener);
        }
        else{
            itemView = LayoutInflater.from(context).inflate(R.layout.list_items_list, parent, false);
            return new ListViewHolder(itemView, mOnItemClickListener);
        }

    }

    @SuppressLint("ResourceType")
    @Override
    public int getItemViewType(int position) {
        if (this.context.getClass().getSimpleName().equals("MainActivity") || this.context.getClass().getSimpleName().equals("SearchableActivity")
            || (this.context.getClass().getSimpleName().equals("ListActivity") && ListActivity.getCurrentCategory().equals("Chair"))) {
            return TYPE_SQUARE;
        } else if(this.context.getClass().getSimpleName().equals("WishListActivity")){
            return  TYPE_WISHLIST;
        } else if(this.context.getClass().getSimpleName().equals("CartActivity")){
            return TYPE_CART;
        } else if(this.context.getClass().getSimpleName().equals("ListActivity") && ListActivity.getCurrentCategory().equals("Storage")){
            return TYPE_RECTANGLE;
        }else{
            return TYPE_LIST;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Item item = itemsList.get(position);
        if (getItemViewType(position) == TYPE_SQUARE) {
            ((SquareViewHolder)holder).nameBrandTxt.setText(item.getBrandName());
            ((SquareViewHolder)holder).nameProductTxt.setText(item.getItemName());
            ((SquareViewHolder)holder).priceTxt.setText(String.valueOf(item.getPrice()));
            Picasso.get().load(item.getImageURL()).into(((SquareViewHolder)holder).itemImage);

        }
        else if (getItemViewType(position) == TYPE_WISHLIST){
            ((WishListViewHolder)holder).nameBrandTxt.setText(item.getBrandName());
            ((WishListViewHolder)holder).nameProductTxt.setText(item.getItemName());
            ((WishListViewHolder)holder).priceTxt.setText(String.valueOf(item.getPrice()));
            Picasso.get().load(item.getImageURL()).into(((WishListViewHolder)holder).itemImage);
        }
        else if(getItemViewType(position) == TYPE_CART) {
            ((CartViewHolder)holder).nameBrandTxt.setText(item.getBrandName());
            ((CartViewHolder)holder).nameProductTxt.setText(item.getItemName());
            ((CartViewHolder)holder).priceTxt.setText(String.valueOf(item.getPrice()));
            Picasso.get().load(item.getImageURL()).into(((CartViewHolder)holder).itemImage);
        }
        else if(getItemViewType(position) == TYPE_LIST){
            ((ListViewHolder)holder).nameBrandTxt.setText(item.getBrandName());
            ((ListViewHolder)holder).nameProductTxt.setText(item.getItemName());
            ((ListViewHolder)holder).priceTxt.setText(String.valueOf(item.getPrice()));
            Picasso.get().load(item.getImageURL()).into(((ListViewHolder)holder).itemImage);
        }

        else{
            ((RectangleViewHolder)holder).nameBrandTxt.setText(item.getBrandName());
            ((RectangleViewHolder)holder).nameProductTxt.setText(item.getItemName());
            ((RectangleViewHolder)holder).priceTxt.setText(String.valueOf(item.getPrice()));
            Picasso.get().load(item.getImageURL()).into(((RectangleViewHolder)holder).itemImage);
        }

    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }


    public interface OnItemClickListener{
        void onItemClick(int position);
    }

}