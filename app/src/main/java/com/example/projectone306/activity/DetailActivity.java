package com.example.projectone306.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.projectone306.R;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.example.projectone306.items.Item;
import com.example.projectone306.wishlistmethod.AddToList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private ImageButton backButton;
    private TextView backText, brandNameText, itemNameText, priceText, recommendationText, descriptionText, specificationText;
    private ImageSlider imageSlider;
    private Item selectedItem;
    private Button heartButton;
    private FloatingActionButton cartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        brandNameText = findViewById(R.id.brand_name);
        itemNameText = findViewById(R.id.item_name);
        priceText = findViewById(R.id.price);
        recommendationText = findViewById(R.id.recommendation_detail);
        descriptionText = findViewById(R.id.description_detail);
        specificationText = findViewById(R.id.specification_detail);
        imageSlider = findViewById(R.id.image_slider);
        backText = findViewById(R.id.last_page);
        heartButton = findViewById(R.id.add_wishlist);
        cartButton = findViewById(R.id.fab);
        backText.setText("Back");

        backButton = findViewById(R.id.return_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        backText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        heartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddToList(itemNameText,"wishList");
            }
        });
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddToList(itemNameText,"cart");
            }
        });

        if (getIntent().hasExtra("selected_item")) {
            selectedItem = getIntent().getParcelableExtra("selected_item");
        }

        setUp();

    }

    private void setUp() {
        NumberFormat formatter = new DecimalFormat("0.00");

        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(selectedItem.getImageURL(), ScaleTypes.CENTER_INSIDE));
        slideModels.add(new SlideModel(selectedItem.getImageURLTwo(), ScaleTypes.CENTER_INSIDE));
        slideModels.add(new SlideModel(selectedItem.getImageURLThree(), ScaleTypes.CENTER_INSIDE));

        brandNameText.setText(selectedItem.getBrandName());
        itemNameText.setText(selectedItem.getItemName());
        priceText.setText(String.valueOf(formatter.format(selectedItem.getPrice())));
        recommendationText.setText(selectedItem.getRecommendation());
        descriptionText.setText(selectedItem.getDescription().replace("\\n", "\n"));
        specificationText.setText(selectedItem.getSpecification().replace("\\n", "\n"));
        imageSlider.setImageList(slideModels, ScaleTypes.CENTER_INSIDE);
    }

    @Override
    public String toString() {
        return "DetailActivity";
    }
}
