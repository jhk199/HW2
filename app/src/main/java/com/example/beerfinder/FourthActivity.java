package com.example.beerfinder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FourthActivity extends AppCompatActivity {

    TextView name, abv, date, description, food_pairing, tips;
    ImageView beer;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        Intent oldIntent = getIntent();
        ArrayList<String> info = oldIntent.getStringArrayListExtra("info");
        linearLayout = findViewById(R.id.linearLayout_more);
        /*
        0 - Name
        1 - Abv
        2 - Date
        3 - description
        4 - food pairing
        5 - tips
        6 - photo
         */
        name = findViewById(R.id.textViewMore_name);
        name.setText(info.get(0));
        abv = findViewById(R.id.textViewMore_abv);
        abv.setText(info.get(1));
        date = findViewById(R.id.textViewMore_date);
        date.setText(info.get(2));
        for(int i = 3; i < 6; i++) {
            TextView textView = new TextView(this);
            textView.setText(info.get(i));
            linearLayout.addView(textView);
        }
        beer = findViewById(R.id.imageViewMore_beer);
        Picasso.get().load(info.get(6)).into(beer);


    }

}
