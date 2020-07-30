package com.elatienda.kaytamarka.bloodbank.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.elatienda.kaytamarka.bloodbank.R;
import com.elatienda.kaytamarka.bloodbank.data.model.post.PostData;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.DataViewHolder> {

    private Context mContext;
    private List<PostData> postDataList = new ArrayList<>();

    public PostAdapter(Context context, List<PostData> postData){
        this.mContext = context;
        this.postDataList = postData;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_custom_post,parent,false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        setData(holder,position);
    }

    public void setData(DataViewHolder holder, int position) {
        PostData postData = postDataList.get(position);
        holder.postTitle.setText(postData.getTitle());
        Glide.with(mContext)
                .load(postData.getThumbnailFullPath())
                .centerCrop()
                .into(holder.postImage);
    }

    public void setAction(DataViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return postDataList.size();
    }

    class DataViewHolder extends RecyclerView.ViewHolder {

        TextView postTitle;
        ImageView postImage;
        RelativeLayout postFavorite;
        ImageView favoriteImg;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            postTitle = itemView.findViewById(R.id.item_custom_post_tv_title);
            postImage = itemView.findViewById(R.id.item_custom_post_iv_post_img);
            postFavorite = itemView.findViewById(R.id.item_custom_post_rl_favorite);
            favoriteImg = itemView.findViewById(R.id.item_custom_post_iv_favorite);

        }
    }

}
