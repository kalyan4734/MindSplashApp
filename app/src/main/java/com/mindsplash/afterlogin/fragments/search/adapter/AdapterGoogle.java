package com.mindsplash.afterlogin.fragments.search.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mindsplash.R;
import com.mindsplash.afterlogin.fragments.search.beans.Item;

import java.util.ArrayList;

public class AdapterGoogle extends RecyclerView.Adapter<AdapterGoogle.GoogleView>{

    private ArrayList<Item> searchList;
    private Context context;
    private OnClickVideo onClickVideo;

    public AdapterGoogle(ArrayList<Item> searchList, Context context,OnClickVideo onClickVideo) {
        this.searchList = searchList;
        this.context = context;
        this.onClickVideo = onClickVideo;

    }

    @NonNull
    @Override
    public GoogleView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.google_view,parent,false);
        return new GoogleView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoogleView holder, int position) {
        Item item = searchList.get(position);
        if(item.getTitle().length()>15)
        holder.txtTitle.setText(item.getTitle().substring(0,15)+"...");
        else
            holder.txtTitle.setText(item.getTitle());
        holder.txtDesc.setText(item.getTitle());
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

    public class GoogleView extends RecyclerView.ViewHolder{

        private TextView txtTitle,txtDesc;
        private RelativeLayout llView;

        public GoogleView(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtDesc = itemView.findViewById(R.id.txt_description);
            llView = itemView.findViewById(R.id.ll_view);

        }

    }
}
