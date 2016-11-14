package com.nathansass.happydolphin.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class IGPost {
    public String url;
    public String id;
    public String username;
    public String profileUrl;
    public int likeCount;

    public IGPost(String url, String id, String username, String profileUrl, int likeCount) {
        this.url = url;
        this.id = id;
        this.username = username;
        this.profileUrl = profileUrl;
        this.likeCount = likeCount;
    }

    public static ArrayList<IGPost> fromJSON(JSONObject response){
        ArrayList<IGPost> igPosts = new ArrayList<>();

        try {
            JSONArray data = response.getJSONArray("data");
            for(int i = 0; i < data.length(); i++) {
                JSONObject postJSON = (JSONObject) data.get(i);
                JSONObject imagesJSON = (JSONObject) postJSON.get("images");
                String url = imagesJSON.getJSONObject("standard_resolution").getString("url");
                String id = postJSON.getString("id");
                JSONObject userJSON = (JSONObject) postJSON.get("user");
                String username = userJSON.getString("username");
                String profileUrl = userJSON.getString("profile_picture");
                JSONObject likesJSON = (JSONObject) postJSON.get("likes");
                int likesCount = likesJSON.getInt("count");

                igPosts.add(new IGPost(url, id, username, profileUrl, likesCount));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return igPosts;
    }
}
