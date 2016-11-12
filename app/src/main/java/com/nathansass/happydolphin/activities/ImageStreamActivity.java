package com.nathansass.happydolphin.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.nathansass.happydolphin.R;
import com.nathansass.happydolphin.models.IGPost;
import com.nathansass.happydolphin.network.ImageSearchGateway;

import org.json.JSONObject;

public class ImageStreamActivity extends AppCompatActivity {
    private String accessToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_stream);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        accessToken = getAccessToken();
        ImageSearchGateway.getRoute("blah", accessToken, new ImageSearchGateway.ImageSearchListener() {
            @Override
            public void done(JSONObject response) {
                IGPost.fromJSON(response);
            }
        });


    }

    private String getAccessToken() {
        SharedPreferences pref =
                PreferenceManager.getDefaultSharedPreferences(this);
        String username = pref.getString(R.string.access_token + "", "n/a");
        return username;
    }

}
