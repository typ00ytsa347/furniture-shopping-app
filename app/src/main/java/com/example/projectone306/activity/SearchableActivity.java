package com.example.projectone306.activity;

import static com.example.projectone306.Constants.NUM_CATEGORIES;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.projectone306.DatabaseHandler;
import com.example.projectone306.Utility;
import com.example.projectone306.adapter.ItemsAdapter;
import com.example.projectone306.ItemMaker;
import com.example.projectone306.R;
import com.example.projectone306.Searcher;
import com.example.projectone306.items.Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SearchableActivity extends AppCompatActivity  implements ItemsAdapter.OnItemClickListener{
    private String query, brandQuery, categoryQuery;
    private float priceLowerBound, priceUpperBound;
    private boolean forAdvancedSearch;

    private int fetchCount;

    private ArrayList<Item> itemsList, allItemsList;
    private ArrayList<String> itemDocIDs;
    private RecyclerView recyclerView;
    private TextView backText, searchedText, activityTitle;
    private ImageButton backButton;
    private ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        fetchCount = 0;

        // Get components from Searchable (Search result) activity and set up
        recyclerView = findViewById(R.id.searchable_activity_rv);
        backText = findViewById(R.id.last_page);
        searchedText = findViewById(R.id.searched_term);
        activityTitle = findViewById(R.id.activity_title);

        backText.setText("Back");
        backButton = findViewById(R.id.return_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        // From Main Activity search view
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            forAdvancedSearch = false;

            query = intent.getStringExtra(SearchManager.QUERY);
            searchedText.setText(query);
            setUpRecyclerView();
        } else if (intent.hasExtra("keyword") && intent.hasExtra("brand") &&
                   intent.hasExtra("category") && intent.hasExtra("priceLower") &&
                   intent.hasExtra("priceUpper")) {
            // From Advanced Search Page (Search Activity)

            forAdvancedSearch = true;

            query = intent.getStringExtra("keyword");
            brandQuery = intent.getStringExtra("brand");
            categoryQuery = intent.getStringExtra("category");
            priceLowerBound = intent.getFloatExtra("priceLower", 0.00f);
            priceUpperBound = intent.getFloatExtra("priceUpper", 0.00f);

            activityTitle.setText("Search Result:");
            searchedText.setText("");
            setUpRecyclerView();
        }

    }

    private void setUpRecyclerView() {
        int numOfCols = 2;

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, numOfCols));

        itemsList = new ArrayList<>();
        allItemsList = new ArrayList<>();
        itemDocIDs = new ArrayList<>();
        itemsAdapter = new ItemsAdapter(SearchableActivity.this, itemsList, this);

        // If the search was invoked from Main Activity, do search from all categories and get
        // matching/query containing items
        // For advanced search, search in the selected category for matching items
        if (!forAdvancedSearch) {
            DatabaseHandler dbHandler = new DatabaseHandler();

            dbHandler.fetchAll(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Item item = ItemMaker.generateObject(document.getReference().getParent().getId());
                            item = document.toObject(item.getClass());
                            allItemsList.add(item);
                            itemDocIDs.add(document.getReference().getId());
                        }

                        // Last read
                        if (fetchCount == NUM_CATEGORIES - 1) {
                            Searcher searcher = new Searcher(query);
                            itemsList.addAll(searcher.search(allItemsList));

                            recyclerView.setAdapter(itemsAdapter);
                            if (itemsAdapter.getItemCount() == 0) {
                                Toast.makeText(getBaseContext(), "Item not found", Toast.LENGTH_LONG).show();
                            }
                        }

                        fetchCount++;
                    } else{
                        // Show failure message if database cannot be reached
                        Toast.makeText(getBaseContext(), "Loading furniture database failed", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            DatabaseHandler dbHandler = new DatabaseHandler(Utility.toDBCollectionName(categoryQuery));

            dbHandler.fetchItemsDataFromOne(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Item item = ItemMaker.generateObject(document.getReference().getParent().getId());
                            item = document.toObject(item.getClass());
                            allItemsList.add(item);
                        }

                        Searcher searcher = new Searcher(query, brandQuery, priceLowerBound, priceUpperBound);
                        itemsList.clear();
                        itemsList.addAll(searcher.searchAdvanced(allItemsList));

                        recyclerView.setAdapter(itemsAdapter);
                        if (itemsAdapter.getItemCount() == 0) {
                            Toast.makeText(getBaseContext(), "Item not found", Toast.LENGTH_LONG).show();
                        }

                    } else{
                        // Show failure message if database cannot be reached
                        Toast.makeText(getBaseContext(), "Loading furniture database failed", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }


    }

    /**
     * This method is invoked when the item card is clicked/tapped
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        // Increase the view count of the selected item
        DatabaseHandler dbHandler = new DatabaseHandler();
        int indexInAllItemsList = allItemsList.indexOf(itemsList.get(position));
        int updatedViewCount = itemsList.get(position).getViewCount() + 1;
        String category = Utility.toDBCollectionName(itemsList.get(position).getClass().getSimpleName());
        dbHandler.updateViewCount(category, itemDocIDs.get(indexInAllItemsList), updatedViewCount);

        // Switch to detail activity of the selected item
        Intent intent = new Intent(SearchableActivity.this, DetailActivity.class);
        intent.putExtra("selected_item", itemsList.get(position));
        SearchableActivity.this.startActivity(intent);
    }
}