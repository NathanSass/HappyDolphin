package com.nathansass.happydolphin.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;

import com.nathansass.happydolphin.R;
import com.nathansass.happydolphin.adapters.PostsAdapter;
import com.nathansass.happydolphin.models.IGPost;
import com.nathansass.happydolphin.network.InstagramGateway;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ImageStreamActivity extends AppCompatActivity {
    public String accessToken;
    public ArrayList<IGPost> igPosts;
    public PostsAdapter postsAdapter;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_stream);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        pb = (ProgressBar) findViewById(R.id.pbLoading);
        showProgressBar();
        accessToken = getAccessToken();

        bindAdapterView();
        getIGPostDataAPI();
        getIGPostDataLocal();
    }

    public void getIGPostDataLocal() {
        igPosts.clear();
        List<IGPost> oldPosts = IGPost.getAll();
        igPosts.addAll(oldPosts);
        postsAdapter.notifyDataSetChanged();
        hideProgressBar(); //we don't really need a progress bar here
    }

    public void getIGPostDataAPI() {
        InstagramGateway.getMediaRoute(accessToken, new InstagramGateway.ImageSearchListener() {
            @Override
            public void done(JSONObject response) {
                igPosts.clear();
                ArrayList<IGPost> newPosts = IGPost.fromJSON(response);
                igPosts.addAll(newPosts);
                postsAdapter.notifyDataSetChanged();

                hideProgressBar();
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

    private void showProgressBar() {
        pb.setVisibility(ProgressBar.VISIBLE);
    }

    private void hideProgressBar() {
        pb.setVisibility(ProgressBar.INVISIBLE);
    }

}
