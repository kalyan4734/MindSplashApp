package com.mindsplash.afterlogin.fragments.search.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mindsplash.R;
import com.mindsplash.network.model.Concept;
import com.mindsplash.network.model.Subject;

import java.util.ArrayList;

public class AdapterSubject extends RecyclerView.Adapter<AdapterSubject.GoogleView>{

    private ArrayList<Subject> searchList;
    private Context context;


    public AdapterSubject(ArrayList<Subject> searchList, Context context)  {
        this.searchList = searchList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterSubject.GoogleView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search,parent,false);
        return new AdapterSubject.GoogleView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSubject.GoogleView holder, int position) {
        Subject item = searchList.get(position);
        holder.txtTitle.setText(item.getSubjectname());
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public class GoogleView extends RecyclerView.ViewHolder{

        private TextView txtTitle,txtDesc;
        private LinearLayout llView;

        public GoogleView(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_title);
            llView = itemView.findViewById(R.id.ll_view);

        }

    }
}

