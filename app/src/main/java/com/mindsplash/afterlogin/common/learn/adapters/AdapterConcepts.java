package com.mindsplash.afterlogin.common.learn.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mindsplash.R;

import java.util.ArrayList;

public class AdapterConcepts extends RecyclerView.Adapter<AdapterConcepts.MyViewHolder> {
    private final Context context;
    private final ArrayList<String> concepts;

    public AdapterConcepts(Context context, ArrayList<String> concepts) {
        this.context = context;
        this.concepts = concepts;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_concecpts, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txtConcept.setText(concepts.get(position));
    }

    @Override
    public int getItemCount() {
        if (concepts != null)
            return concepts.size();
        else
            return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtConcept;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtConcept = itemView.findViewById(R.id.txt_concept);
        }
    }
}
