package com.twodudesdev.bitcoinalert;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    final Context context = this;
    TextView textViewDisplayPrice;
    BitCoinInfo btcCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AsyncHttpClient client = new AsyncHttpClient();
        textViewDisplayPrice = this.findViewById(R.id.textDisplayPrice);
        getData(client);
    }

    private void getData(AsyncHttpClient client) {
        client.get("https://api.bitfinex.com/v1/ticker/btcusd", new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                String toastText = "Successfully downloaded JSON File";
                Toast successfulJsonToast = Toast.makeText(context, toastText, Toast.LENGTH_SHORT);
                successfulJsonToast.show();
                Gson btcGson = new GsonBuilder().create();
                btcCurrent = btcGson.fromJson(response, BitCoinInfo.class);
                /*
                So now we have our former Json string as a Java object, with everything in String
                variables. The next part converts btcCurrent.last_price into a string for display in
                activity_main, including the two-digit decimal and the dollar sign. I know it's
                messy to convert to a Double then back to a string, but it works.
                 */
                Double price = Double.parseDouble(btcCurrent.last_price);
                String lastPrice = String.format(Locale.US,"%.2f", price);
                lastPrice = "$"+lastPrice;

                textViewDisplayPrice.setText(lastPrice);
                /*
                textViewDisplayPrice has to be set here, because this is running asynchronously
                from the rest of the app. If I put it on line 30, the Json is still getting
                processed by the time textViewDisplayPrice tries to update.
                 */
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
