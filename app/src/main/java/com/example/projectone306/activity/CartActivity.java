package com.example.projectone306.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.projectone306.DatabaseHandler;
import com.example.projectone306.adapter.ItemsAdapter;
import com.example.projectone306.R;
import com.example.projectone306.items.Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity  implements ItemsAdapter.OnItemClickListener{
    private ArrayList<String> itemNames;
    private ArrayList<Item> itemsList;
    private RecyclerView recyclerView;
    private TextView emptyMessage;
    private ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cart_activity_rv);
        emptyMessage = findViewById(R.id.cart_empty_message);

        Intent intent = getIntent();
        itemNames = intent.getStringArrayListExtra("nameArray");
        setUpRecyclerView();
        System.out.println(itemNames);
    }

    private void setUpRecyclerView() {
        int numOfCols = 1;

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, numOfCols));

        itemsList = new ArrayList<>();
        itemsAdapter = new ItemsAdapter(CartActivity.this, itemsList, this);
        DatabaseHandler dbHandler = new DatabaseHandler();

        if (itemNames != null) {
                for (String item : itemNames) {
                    dbHandler.fetchSpecificItemForMainAct(item, new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Item item = document.toObject(Item.class);
                                    itemsList.add(item);
                                }

                                // Populate RecyclerView
                                if (itemsAdapter.getItemCount() > 0) {
                                    recyclerView.setAdapter(itemsAdapter);
                                    emptyMessage.setVisibility(View.INVISIBLE);
                                }

                                DatabaseHandler.counter++; // Increments read collections number

                                // After reading all collections, check if any items are found
                                // If not, show message
                                if (DatabaseHandler.counter == DatabaseHandler.numItemsCollections &&
                                        itemsAdapter.getItemCount() == 0) {
                                    DatabaseHandler.counter = 0;
                                }
                            } else {
                                // Show failure message if database cannot be reached
                                Toast.makeText(getBaseContext(), "Loading furniture database failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }

        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(CartActivity.this, DetailActivity.class);
        intent.putExtra("selected_item", itemsList.get(position));
        CartActivity.this.startActivity(intent);
    }

    @Override
    public String toString() { return "CartActivity"; }
}