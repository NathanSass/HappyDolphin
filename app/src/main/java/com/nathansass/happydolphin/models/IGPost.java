package com.nathansass.happydolphin.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class IGPost {
    public String url;
    public String id;

    public IGPost(String url, String id) {
        this.url = url;
        this.id = id;
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
                igPosts.add(new IGPost(url, id));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return igPosts;
    }
}
