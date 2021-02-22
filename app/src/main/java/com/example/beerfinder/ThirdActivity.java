package com.example.beerfinder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ThirdActivity extends AppCompatActivity {

    SearchView filter;
    TextView title;
    private ArrayList<Beer> beers;
    private RecyclerView recyclerView;
    private static AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        filter = findViewById(R.id.searchView);
        title = findViewById(R.id.textView_wefound);
        recyclerView = findViewById(R.id.recyclerView_beers);
        beers = new ArrayList<>();
        loadRecyclerView();



    }

    void loadRecyclerView() {
        Intent oldIntent = getIntent();
        String url = oldIntent.getStringExtra("url");
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONArray beerArray = new JSONArray(new String(responseBody));
                    for(int i = 0; i < beerArray.length(); i++) {
                        JSONObject beerObject = beerArray.getJSONObject(i);
                        Beer beer = new Beer(
                                beerObject.getString("name"),
                                beerObject.getString("abv"),
                                beerObject.getString("first_brewed"),
                                beerObject.getString("image_url"),
                                beerObject.getString("description"),
                                beerObject.getString("food_pairing"),
                                beerObject.getString("brewers_tips")
                        );
                        beers.add(beer);
                    }

                    BeerAdapter adapter = new BeerAdapter(beers);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ThirdActivity.this));
                    recyclerView.setHasFixedSize(true);
                    // add decorations
                    // DividerItemDecorations
                    // add a line between each row
                    RecyclerView.ItemDecoration itemDecoration =
                            new DividerItemDecoration(ThirdActivity.this,
                                DividerItemDecoration.VERTICAL);
                    recyclerView.addItemDecoration(itemDecoration);
                    String text = "We found " + adapter.getSize() + " results";
                    title.setText(text);
                    filter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            adapter.filter(query);
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            adapter.filter(newText);
                            updateSize(adapter.getItemCount());
                            return false;
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("Error", "Failed to connect");
            }
        });
    }

    void updateSize(int size) {
        String text = "We found " + size + " results";
        title.setText(text);
    }
}
