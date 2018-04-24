package com.twodudesdev.bitcoinalert;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.Random;

import cz.msebera.android.httpclient.Header;



public class MainActivity extends AppCompatActivity {
    Context context = this;
    String Burger = "yes";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AsyncHttpClient client = new AsyncHttpClient();
        dataGet(client);
        JobManager.create(this).addJobCreator(new DemoJobCreator());
    }
    private void dataGet(AsyncHttpClient client) {
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

class downloadAndSaveData extends Job {
    static final String TAG = "Download_Current_Price";


    @NonNull
    @Override
    protected Result onRunJob(Params params){
        String ted = "stuff";
        PendingIntent pi = PendingIntent.getActivity(getContext(),0, new Intent(getContext(), MainActivity.class),0);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getContext(),ted)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Android Job Demo")
                .setContentText("Notification from Android Job Demo App.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat jobNoticeManager = NotificationManagerCompat.from(getContext());
        jobNoticeManager.notify(7, notification.build());


        return Result.SUCCESS;
        }

        static void schedulePeriodic(){
        new JobRequest.Builder(downloadAndSaveData.TAG)
                .setPeriodic(120000)
                .setUpdateCurrent(true)
                .setPersisted(true)
                .build()
                .schedule();
        }
}


class DemoJobCreator implements JobCreator{
    @Override
    public Job create(String tag){
        switch (tag){
            case downloadAndSaveData.TAG:
                return new downloadAndSaveData();
            default:
                return null;
        }
    }
}

