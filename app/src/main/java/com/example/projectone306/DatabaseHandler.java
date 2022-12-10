package com.example.projectone306;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * Class for firestore database transactions
 */
public class DatabaseHandler {
    private String category;
    private String[] collections;
    public String currentCategory;
    public static final int numItemsCollections = 8;
    public boolean lastRead;
    public static int counter = 0; // Keeps track of read collections

    FirebaseFirestore db;

    public DatabaseHandler() {
        collections = new String[] {"chairs", "sofas", "beds", "storages", "tables", "carpets", "lamps", "others"};

        db = FirebaseFirestore.getInstance();
    }

    public DatabaseHandler(String category) {
        this.category = category;

        db = FirebaseFirestore.getInstance();
    }

    /**
     * Retrieves all documents from a collection defined by category string
     * @return
     */
    public void fetchItemsDataFromOne(OnCompleteListener<QuerySnapshot> listener) {
        db.collection(category).get().addOnCompleteListener(listener);
    }

    /**
     * Retrieves specific item from collections
     * @param listener
     */
    public void fetchSpecificItemForMainAct(String query, OnCompleteListener<QuerySnapshot> listener) {
        query = query.substring(0, 1).toUpperCase() + query.substring(1).toLowerCase();

        for (String collection : collections) {
            db.collection(collection).whereEqualTo("itemName", query).get().addOnCompleteListener(listener);
        }
    }

    /**
     * Retrieves all items from all documents
     * @param listener
     */
    public void fetchAll(OnCompleteListener<QuerySnapshot> listener) {

        for (int i = 0; i < collections.length; i++) {
            db.collection(collections[i]).get().addOnCompleteListener(listener);
        }
    }

    /**
     * Updates the view count for chosen document in the collection
     * @param collectionName
     * @param docID
     * @param newViewCount
     */
    public void updateViewCount(String collectionName, String docID, int newViewCount) {
        db.collection(collectionName).document(docID).update("viewCount", newViewCount);
    }
}
