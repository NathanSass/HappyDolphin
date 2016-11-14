package com.nathansass.happydolphin.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nathansass.happydolphin.R;
import com.nathansass.happydolphin.models.IGPost;
import com.nathansass.happydolphin.util.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by nathansass on 11/12/16.
 */

public class PostsAdapter extends
        RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivPostImage;
        public ImageView ivProfileImage;
        public TextView tvProfileName;
        public TextView tvLikeCount;


        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            ivPostImage = (ImageView) itemView.findViewById(R.id.ivPostImage);
            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvProfileName = (TextView) itemView.findViewById(R.id.tvProfileName);
            tvLikeCount = (TextView) itemView.findViewById(R.id.tvLikeCount);
        }
    }

    private List<IGPost> igPosts;
    private Context context;

    public PostsAdapter(Context context, List<IGPost> posts) {
        this.igPosts = posts;
        this.context = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return this.context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_ig_post, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        IGPost igPost = igPosts.get(position);
        ImageView ivPostImage = holder.ivPostImage;
        ImageView ivProfileImage = holder.ivProfileImage;

        TextView tvProfileName = holder.tvProfileName;
        tvProfileName.setText(igPost.username);

        TextView tvLikeCount = holder.tvLikeCount;
        tvLikeCount.setText(igPost.likeCount + "");

        Picasso.with(getContext()).load(igPost.url)
                .placeholder(R.drawable.placeholder)
                .into(ivPostImage);

        Picasso.with(getContext()).load(igPost.profileUrl)
                .transform(new CircleTransform())
                .into(ivProfileImage);
    }

    @Override
    public int getItemCount() {
        return igPosts.size();
    }
}