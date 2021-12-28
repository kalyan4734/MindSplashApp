package com.mindsplash.afterlogin.fragments.search.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mindsplash.R;
import com.mindsplash.network.model.Chapter;

import java.util.ArrayList;

public class AdapterChapter extends RecyclerView.Adapter<AdapterChapter.GoogleView>{

    private ArrayList<Chapter> searchList;
    private Context context;


    public AdapterChapter(ArrayList<Chapter> searchList, Context context)  {
        this.searchList = searchList;
        this.context = context;

    }

    @NonNull
    @Override
    public AdapterChapter.GoogleView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search,parent,false);
        return new AdapterChapter.GoogleView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoogleView holder, int position) {
        Chapter item = searchList.get(position);
        holder.txtTitle.setText(item.getChaptername());
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
            llView = itemView.findViewById(R.id.ll_view);

        }

    }
}

