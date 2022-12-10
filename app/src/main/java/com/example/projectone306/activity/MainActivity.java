package com.example.projectone306.activity;

import static com.example.projectone306.Constants.NUM_CATEGORIES;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.projectone306.DatabaseHandler;
import com.example.projectone306.ItemMaker;
import com.example.projectone306.Utility;
import com.example.projectone306.adapter.ItemsAdapter;
import com.example.projectone306.R;
import com.example.projectone306.items.Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements ItemsAdapter.OnItemClickListener {

    private int fetchCount;
    private RecyclerView recyclerView;
    private ArrayList<Item> itemsList;
    private ArrayList<String> itemDocIDs;
    private ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hides top bar
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview_popular);

        fetchCount = 0;

        // Enable assisted search
        enableAssistedSearch();

        // Populate recycler view
        setUpRecyclerView();
    }

    /**
     * Sets up the search view
     */
    private void enableAssistedSearch() {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) findViewById(R.id.mainActivity_searchView);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    }

    private void setUpRecyclerView() {
        itemsList = new ArrayList<>();
        itemDocIDs = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        itemsAdapter = new ItemsAdapter(MainActivity.this, itemsList, this);


        DatabaseHandler dbHandler = new DatabaseHandler();

        // Access the database and retrieve all the items from all categories
        dbHandler.fetchAll(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Item item = ItemMaker.generateObject(document.getReference().getParent().getId());
                        item = document.toObject(item.getClass());
                        itemsList.add(item);
                        itemDocIDs.add(document.getReference().getId());
                    }

                    // Last read
                    if (fetchCount == NUM_CATEGORIES - 1) {
                        // Sort the all items list by their view count
                        Collections.sort(itemsList, new Comparator<Item>() {
                            @Override
                            public int compare(Item item1, Item item2) {
                                return item2.viewCount - item1.viewCount;
                            }
                        });

                        // Shows 10 popular items on main activity
                        itemsList.subList(10, itemsList.size()).clear();
                        recyclerView.setAdapter(itemsAdapter);
                    }

                    fetchCount++;
                } else{
                    // Show failure message if database cannot be reached
                    Toast.makeText(getBaseContext(), "Loading furniture database failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * This method is invoked when the category button is clicked/tapped
     * Switches to list activity of the selected category
     * @param view
     */
    public void goToListActivity(View view) {
        Button pressedButton = (Button)view;
        Intent intent = new Intent(MainActivity.this, ListActivity.class);
        intent.putExtra("category", pressedButton.getText().toString());
        MainActivity.this.startActivity(intent);
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
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("selected_item", itemsList.get(position));
        MainActivity.this.startActivity(intent);
    }
}