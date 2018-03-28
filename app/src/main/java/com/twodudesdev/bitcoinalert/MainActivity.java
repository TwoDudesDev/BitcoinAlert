package com.twodudesdev.bitcoinalert;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import cz.msebera.android.httpclient.Header;



public class MainActivity extends AppCompatActivity {
    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://api.bitfinex.com/v1/ticker/btcusd", new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                String toastText = "Successfully downloaded JSON File";
                Toast successfulJsonToast = Toast.makeText(context, toastText, Toast.LENGTH_SHORT);
                successfulJsonToast.show();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String response, Throwable throwable) {
                String toastText = "Cannot load JSON File: " + throwable;
                Toast failedJsonToast = Toast.makeText(context, toastText, Toast.LENGTH_SHORT);
                failedJsonToast.show();
            }
        });
    }
}
