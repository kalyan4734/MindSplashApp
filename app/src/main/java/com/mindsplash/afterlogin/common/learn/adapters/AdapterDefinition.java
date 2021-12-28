package com.mindsplash.afterlogin.common.learn.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mindsplash.R;

import java.util.List;

public class AdapterDefinition extends RecyclerView.Adapter<AdapterDefinition.DefViewHolder> {
    private Context context;
    private List<String> data;

    public AdapterDefinition(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public DefViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_definition,parent,false);
        return new DefViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DefViewHolder holder, int position) {
        holder.txtName.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class DefViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName;
        public DefViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_definition);
        }
    }
}
