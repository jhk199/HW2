package com.example.beerfinder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SecondActivity extends AppCompatActivity {

    private EditText search, date_start, date_end;
    private SwitchCompat highpoint;
    static boolean abv = false, canGo = true;
    private Button find_beers;


    private ArrayList<Beer> beers;
    private static final String api_url = "https://api.punkapi.com/v2/beers?";
    RequestParams params = new RequestParams();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        search = findViewById(R.id.editText_search);
        date_start = findViewById(R.id.editText_start);
        date_end = findViewById(R.id.editText_end);
        highpoint = findViewById(R.id.switch_highpoint);
        find_beers = findViewById(R.id.button_find_beer);

        highpoint.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    abv = true;
                }
                else {
                    abv = false;
                }
            }
        });

    }

    public void launchNextActivity(View view) {
        addParams(view);
        canGo = true;
        if(canGo) {
            beers = new ArrayList<>();
            String final_url = api_url + params.toString();
            Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
            intent.putExtra("url", final_url);
            startActivity(intent);
        }
    }


    public void addParams(View view) {

        if(!search.getText().toString().equals("")) {
            params.put("beer_name", search.getText());
        }
        if(abv) {
            params.put("abv_gt", 3.99);
        }
        if((!date_start.getText().toString().equals("")) &&
                (!date_end.getText().toString().equals("")) &&
                dateDifference(date_start, date_end)) {
            params.put("brewed_before", date_end.getText());
            params.put("brewed_after", date_start.getText());
        }
        if((!date_start.getText().toString().equals("")) && dateValid(date_start)
                && (date_end.getText().toString().equals(""))) {
            params.put("brewed_after", date_start.getText());

        }
        if((!date_end.getText().toString().equals("")) && dateValid(date_end)
            && (date_start.getText().toString().equals(""))) {
            params.put("brewed_before", date_end.getText());
        }

        Log.d("Params", api_url+ params.toString());
    }

    public boolean dateValid(EditText date) {
       String dateString = date.getText().toString();
       int check = 0;
       if(dateString.indexOf('/') == 2) {
           check++;
       }
       if(dateString.length() == 7) {
           check++;
       }
       if((Integer.parseInt(dateString.substring(0,2)) <= 12) &&
               (Integer.parseInt(dateString.substring(0,2)) >= 1)) {
           check ++;
       }
       if(check == 3) {
           canGo = true;

       }
       else {
           canGo = false;
           Toast toast = Toast.makeText(this, R.string.toast_message1, Toast.LENGTH_LONG);
           toast.show();
       }
       return canGo;
    }
    public boolean dateDifference(EditText one, EditText two) {
        if(dateValid(one) && dateValid(two)) {
            String oneText = one.getText().toString();
            String twoText = two.getText().toString();
            int oneMonth = Integer.parseInt(oneText.substring(0,2));
            int twoMonth = Integer.parseInt(twoText.substring(0,2));
            int oneYear = Integer.parseInt(oneText.substring(3,7));
            int twoYear = Integer.parseInt(twoText.substring(3,7));
            Log.d("One year", String.valueOf(oneYear));
            Log.d("Two year", String.valueOf(twoYear));
            if(oneYear < twoYear) {
                return true;
            }
            else if ((oneYear == twoYear) && oneMonth < twoMonth) {
                return true;
            }
        }
        return false;
    }
}
