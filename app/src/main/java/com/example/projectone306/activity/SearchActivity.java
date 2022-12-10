package com.example.projectone306.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.projectone306.R;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.RangeSlider;

import java.text.NumberFormat;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {
    TextView keywordText;
    Spinner brandSpinner, categorySpinner;
    RangeSlider priceSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Get components and setup
        keywordText = findViewById(R.id.keyword_edit);
        brandSpinner = findViewById(R.id.brand_spinner);
        categorySpinner = findViewById(R.id.category_spinner);

        priceSlider = findViewById(R.id.price_slider);
        priceSlider.setLabelFormatter(new LabelFormatter() {
            @NonNull
            @Override
            public String getFormattedValue(float value) {
                Locale locale = new Locale("en", "NZ");
                NumberFormat priceFormatter = NumberFormat.getCurrencyInstance(locale);
                return priceFormatter.format(value);
            }
        });
    }

    @Override
    public String toString() {
        return "SearchActivity";
    }

    /**
     * Switches to search result activity (Searchable) and pass them the input queries
     * @param view
     */
    public void toSearchable(View view) {
        Intent intent = new Intent(SearchActivity.this, SearchableActivity.class);
        intent.putExtra("keyword", keywordText.getText().toString());
        intent.putExtra("brand", brandSpinner.getSelectedItem().toString());
        intent.putExtra("category", categorySpinner.getSelectedItem().toString());
        intent.putExtra("priceLower", priceSlider.getValues().get(0));
        intent.putExtra("priceUpper", priceSlider.getValues().get(1));

        SearchActivity.this.startActivity(intent);
    }

}