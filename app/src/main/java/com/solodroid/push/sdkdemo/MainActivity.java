package com.solodroid.push.sdkdemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.solodroid.push.sdk.provider.OneSignalPush;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    OneSignalPush.Builder onesignal;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onesignal = new OneSignalPush.Builder(this);
        onesignal.requestNotificationPermission();
        notificationOpenHandler(getIntent());
        initToolbar();
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setTitle(getString(R.string.app_name));
        }
    }

    public void notificationOpenHandler(Intent getIntent) {

        if (getIntent.hasExtra(OneSignalPush.EXTRA_ID)) {

            String id = getIntent.getStringExtra(OneSignalPush.EXTRA_ID);
            String title = getIntent.getStringExtra(OneSignalPush.EXTRA_TITLE);
            String message = getIntent.getStringExtra(OneSignalPush.EXTRA_MESSAGE);
            String bigImage = getIntent.getStringExtra(OneSignalPush.EXTRA_IMAGE);
            String launchUrl = getIntent.getStringExtra(OneSignalPush.EXTRA_LAUNCH_URL);

            String uniqueId = getIntent.getStringExtra(OneSignalPush.EXTRA_UNIQUE_ID);
            String postId = getIntent.getStringExtra(OneSignalPush.EXTRA_POST_ID);
            String link = getIntent.getStringExtra(OneSignalPush.EXTRA_LINK);

            if (link != null && !link.equals("")) {
                if (!link.equals("0")) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
                }
            }

            if (postId != null && !postId.equals("")) {
                if (!postId.equals("0")) {
                    Intent intent = new Intent(getApplicationContext(), ActivityDetails.class);
                    intent.putExtra(OneSignalPush.EXTRA_UNIQUE_ID, uniqueId);
                    intent.putExtra(OneSignalPush.EXTRA_POST_ID, postId);
                    intent.putExtra(OneSignalPush.EXTRA_LINK, link);
                    startActivity(intent);
                }
            }


        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}