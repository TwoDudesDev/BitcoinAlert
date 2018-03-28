package com.twodudesdev.bitcoinalert;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import com.loopj.android.http.*;
import cz.msebera.android.httpclient.Header;



public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    AsyncHttpClient client = new AsyncHttpClient();
    client.get("https://api.bitfinex.com/v1/ticker/btcusd", new TextHttpResponseHandler(){
        @Override
                public void onFailure(int statusCode, Header[] headers, String response, Throwable throwable){
            Toast.makeText(this, "Cannot load JSON File", Toast.LENGTH_SHORT).show();
            /* It wouldn't let me save until I changed something so, bad line of code here;
            turned the bad line into a comment
             */

        }

    }

}
