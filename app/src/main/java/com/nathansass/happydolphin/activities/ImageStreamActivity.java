package com.nathansass.happydolphin.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.nathansass.happydolphin.R;
import com.nathansass.happydolphin.adapters.PostsAdapter;
import com.nathansass.happydolphin.models.IGPost;
import com.nathansass.happydolphin.network.InstagramGateway;

import org.json.JSONObject;

import java.util.ArrayList;

public class ImageStreamActivity extends AppCompatActivity {
    private String accessToken;
    public ArrayList<IGPost> igPosts;
    public PostsAdapter postsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_stream);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bindAdapterView();
        getIGPostData();
    }

    public void getIGPostData() {
        accessToken = getAccessToken();

        InstagramGateway.getMediaRoute(accessToken, new InstagramGateway.ImageSearchListener() {
            @Override
            public void done(JSONObject response) {
                int curSize = igPosts.size();
                ArrayList<IGPost> newPosts = IGPost.fromJSON(response);
                igPosts.addAll(newPosts);
                postsAdapter.notifyItemRangeInserted(curSize, newPosts.size());
            }
        });
    }

    public void bindAdapterView() {
        RecyclerView rvImageStream = (RecyclerView) findViewById(R.id.rvImageStream);
        igPosts = new ArrayList<>();
        postsAdapter = new PostsAdapter(this, igPosts);
        rvImageStream.setAdapter(postsAdapter);
        rvImageStream.setLayoutManager(new LinearLayoutManager(this));
    }

    public String getAccessToken() {
        SharedPreferences pref =
                PreferenceManager.getDefaultSharedPreferences(this);
        String accessToken = pref.getString(R.string.access_token + "", "n/a");
        return accessToken;
    }

}
