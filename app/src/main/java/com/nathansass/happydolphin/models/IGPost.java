package com.nathansass.happydolphin.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Table(name = "IGPost")
public class IGPost extends Model{
    @Column(name = "Url")
    public String url;
    @Column(name = "PostId", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public String postId;
    @Column(name = "Username")
    public String username;
    @Column(name = "ProfileUrl")
    public String profileUrl;
    @Column(name = "LikeCount")
    public int likeCount;
    @Column(name = "UserHasLiked")
    public boolean userHasLiked;

    public IGPost(String url, String postId, String username, String profileUrl, int likeCount, boolean userHasLiked) {
        super();
        this.url = url;
        this.postId = postId;
        this.username = username;
        this.profileUrl = profileUrl;
        this.likeCount = likeCount;
        this.userHasLiked = userHasLiked;
    }

    public IGPost() {
        super();
    }

    public static ArrayList<IGPost> fromJSON(JSONObject response){
        ArrayList<IGPost> igPosts = new ArrayList<>();

        try {
            JSONArray data = response.getJSONArray("data");
            for(int i = 0; i < data.length(); i++) {
                JSONObject postJSON = (JSONObject) data.get(i);
                JSONObject imagesJSON = (JSONObject) postJSON.get("images");
                Boolean userHasLiked = postJSON.getBoolean("user_has_liked");
                String url = imagesJSON.getJSONObject("standard_resolution").getString("url");
                String id = postJSON.getString("id");
                JSONObject userJSON = (JSONObject) postJSON.get("user");
                String username = userJSON.getString("username");
                String profileUrl = userJSON.getString("profile_picture");
                JSONObject likesJSON = (JSONObject) postJSON.get("likes");
                int likesCount = likesJSON.getInt("count");

                IGPost igPost = new IGPost(url, id, username, profileUrl, likesCount, userHasLiked);
                igPost.save();
                igPosts.add(igPost);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return igPosts;
    }

    public static List<IGPost> getAll() { //should only query fresh items,
        return new Select()
                .from(IGPost.class)
                .limit(10)
                .execute();

    }
}
