package com.solodroid.push.sdkdemo;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.solodroid.push.sdk.provider.OneSignalPush;

public class ActivityDetails extends AppCompatActivity {

    Toolbar toolbar;
    TextView txtPostId;
    String uniqueId = "";
    String postId = "";
    String link = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        if (getIntent() != null) {
            uniqueId = getIntent().getStringExtra(OneSignalPush.EXTRA_UNIQUE_ID);
            postId = getIntent().getStringExtra(OneSignalPush.EXTRA_POST_ID);
            link = getIntent().getStringExtra(OneSignalPush.EXTRA_LINK);
        }
        initView();
        initToolbar();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        txtPostId = findViewById(R.id.txt_post_id);
        txtPostId.setText("Unique ID: " + uniqueId + "\nPost ID: " + postId + "\nLink: " + link);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle("Details");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

}
