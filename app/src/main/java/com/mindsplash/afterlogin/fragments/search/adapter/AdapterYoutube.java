package com.mindsplash.afterlogin.fragments.search.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mindsplash.R;
import com.mindsplash.afterlogin.fragments.search.beans.Item;

import java.util.ArrayList;

public class AdapterYoutube extends RecyclerView.Adapter<AdapterYoutube.GoogleView> {

    private ArrayList<Item> searchList;
    private Context context;
    private OnClickVideo onClickVideo;

    public AdapterYoutube(ArrayList<Item> searchList, Context context, OnClickVideo onClickVideo) {
        this.searchList = searchList;
        this.context = context;
        this.onClickVideo = onClickVideo;

    }

    @NonNull
    @Override
    public AdapterYoutube.GoogleView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.youtube_view, parent, false);
        return new AdapterYoutube.GoogleView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterYoutube.GoogleView holder, int position) {
        Item item = searchList.get(position);
        holder.txtTitle.setText(item.getTitle());
        Glide.with(context).load(item.pagemap.cse_thumbnail.get(0).src).into(holder.ivThumbnail);
        holder.llView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickVideo.onClick(item.getHtmlFormattedUrl())
                ;
            }
        });


    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public class GoogleView extends RecyclerView.ViewHolder {

        private TextView txtTitle;
        private RelativeLayout llView;
        private ImageView ivThumbnail;

        public GoogleView(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_description);
            llView = itemView.findViewById(R.id.ll_view);
            ivThumbnail = itemView.findViewById(R.id.iv_thumbnail);

        }

    }
}
