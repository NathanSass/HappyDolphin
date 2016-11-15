package com.nathansass.happydolphin.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nathansass.happydolphin.R;
import com.nathansass.happydolphin.models.IGPost;
import com.nathansass.happydolphin.network.InstagramGateway;
import com.nathansass.happydolphin.util.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by nathansass on 11/12/16.
 */

public class PostsAdapter extends
        RecyclerView.Adapter<PostsAdapter.ViewHolder> {


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView ivPostImage;
        public ImageView ivProfileImage;
        public TextView tvProfileName;
        public TextView tvLikeCount;
        public ImageView ivLikeIcon;
        private Context context;

        public ViewHolder(Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            ivPostImage = (ImageView) itemView.findViewById(R.id.ivPostImage);
            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvProfileName = (TextView) itemView.findViewById(R.id.tvProfileName);
            tvLikeCount = (TextView) itemView.findViewById(R.id.tvLikeCount);
            ivLikeIcon = (ImageView) itemView.findViewById(R.id.ivLikeIcon);

            this.context = context;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition(); // gets item position
            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                IGPost igPost = igPosts.get(position);
                SharedPreferences pref =
                        PreferenceManager.getDefaultSharedPreferences(getContext()); //TODO: put this in a shared function
                String accessToken = pref.getString(R.string.access_token + "", "n/a");

                Vibrator vibrator = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(75);

                if (igPost.userHasLiked) {
                    igPost.userHasLiked = false;
                    igPost.likeCount -= 1;
                    InstagramGateway.unlikeMediaRoute(getContext(), igPost.postId, accessToken);
                } else {
                    igPost.userHasLiked = true;
                    igPost.likeCount += 1;
                    InstagramGateway.likeMediaRoute(getContext(), igPost.postId, accessToken);
                }
                igPost.save();
                notifyItemChanged(position);
            }
        }
    }


    private List<IGPost> igPosts;
    private Context context;
    private TextView tvLikeCount;
    private ImageView ivLikeIcon;

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
        ViewHolder viewHolder = new ViewHolder(getContext(), contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        IGPost igPost = igPosts.get(position);
        ImageView ivPostImage = holder.ivPostImage;
        ImageView ivProfileImage = holder.ivProfileImage;
        ivLikeIcon = holder.ivLikeIcon;

        TextView tvProfileName = holder.tvProfileName;
        tvProfileName.setText(igPost.username);

        tvLikeCount = holder.tvLikeCount;
        tvLikeCount.setText(igPost.likeCount + "");

        if(igPost.userHasLiked) {
            ivLikeIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_heart_fill));
        } else {
            ivLikeIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_heart_stroke));
        }

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