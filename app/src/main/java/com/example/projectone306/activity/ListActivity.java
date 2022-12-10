package com.example.projectone306.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectone306.DatabaseHandler;
import com.example.projectone306.adapter.ItemsAdapter;
import com.example.projectone306.ItemMaker;
import com.example.projectone306.Utility;
import com.example.projectone306.items.Chair;
import com.example.projectone306.items.Item;
import com.example.projectone306.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ListActivity extends AppCompatActivity implements ItemsAdapter.OnItemClickListener{
    private String selectedCategory;

    private ArrayList<Item> itemsList;
    private ArrayList<String> itemDocIDs;
    private RecyclerView recyclerView;
    private TextView previousPageText;
    public static TextView curCategoryText;
    private ImageButton backButton;
    private ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Intent intent = getIntent();

        // Get components from list activity and set up
        recyclerView = findViewById(R.id.list_activity_rv);
        previousPageText = findViewById(R.id.last_page);
        curCategoryText = findViewById(R.id.current_category);
        previousPageText.setText("Home");
        curCategoryText.setText(intent.getStringExtra("category"));

        backButton = findViewById(R.id.return_button);
        backButton.setOnClickListener(new View.OnClickListener() {
                                          public void onClick(View v) {
                                              finish();
                                          }
                                      });

        itemsList = new ArrayList<>();

        selectedCategory = Utility.toDBCollectionName(intent.getStringExtra("category"));

        // Set up and populate recycler view
        setUpRecyclerView();
    }

    public static String getCurrentCategory(){
        return curCategoryText.getText().toString();
    }

    private void setUpRecyclerView() {

        recyclerView.setHasFixedSize(true);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        }
        else {
            int numOfCols = 1;
            if(curCategoryText.getText().equals("Chair") || curCategoryText.getText().equals("Storage") ){
                numOfCols = 2;
            }
            recyclerView.setLayoutManager(new GridLayoutManager(this, numOfCols));
        }

        itemsList = new ArrayList<>();
        itemDocIDs = new ArrayList<>();

        itemsAdapter = new ItemsAdapter(ListActivity.this, itemsList, this);

        DatabaseHandler dbHandler = new DatabaseHandler(selectedCategory);

        // Access the database and retrieve selected category items
        dbHandler.fetchItemsDataFromOne(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Item item = ItemMaker.generateObject(selectedCategory);
                        item = document.toObject(item.getClass());
                        itemsList.add(item);
                        itemDocIDs.add(document.getReference().getId());
                    }

                    // If there is an item in database, populate the RecyclerView
                    if (itemsList.size() > 0) {
                        recyclerView.setAdapter(itemsAdapter);
                    } else {
                        // If not, Show empty error message
                        Toast.makeText(getBaseContext(), "Furniture database is empty", Toast.LENGTH_LONG).show();
                    }
                } else{
                    // Show failure message if database cannot be reached
                    Toast.makeText(getBaseContext(), "Loading furniture database failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * This method is invoked when the item card is clicked/tapped
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        // Increase the view count of the selected item
        DatabaseHandler dbHandler = new DatabaseHandler();
        int updatedViewCount = itemsList.get(position).getViewCount() + 1;
        String category = Utility.toDBCollectionName(itemsList.get(position).getClass().getSimpleName());
        dbHandler.updateViewCount(category, itemDocIDs.get(position), updatedViewCount);

        // Switch to detail activity of the selected item
        Intent intent = new Intent(ListActivity.this, DetailActivity.class);
        intent.putExtra("selected_item", itemsList.get(position));
        ListActivity.this.startActivity(intent);
    }
}