package com.mindsplash.afterlogin.fragments.home.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mindsplash.R;
import com.mindsplash.afterlogin.fragments.search.adapter.OnClickVideo;
import com.mindsplash.afterlogin.fragments.search.beans.Item;

import java.util.ArrayList;

public class AdapterDownload extends RecyclerView.Adapter<AdapterDownload.GoogleView>{

    private ArrayList<Item> searchList;
    private Context context;
    private OnClickVideo onClickVideo;

    public AdapterDownload(ArrayList<Item> searchList, Context context) {
        this.searchList = searchList;
        this.context = context;
        this.onClickVideo = onClickVideo;

    }

    @NonNull
    @Override
    public GoogleView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_view,parent,false);
        return new GoogleView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoogleView holder, int position) {
//        Item item = searchList.get(position);
        holder.txtTitle.setText("Notification");
        holder.txtNo.setText(position+1+"");
//        holder.llView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onClickVideo.onClick(item.getHtmlFormattedUrl())
//                ;
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class GoogleView extends RecyclerView.ViewHolder{

        private TextView txtTitle,txtNo;
        private LinearLayout llView;

        public GoogleView(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtNo = itemView.findViewById(R.id.txt_no_of_chapters);
            llView = itemView.findViewById(R.id.ll_view);

        }

    }
}
