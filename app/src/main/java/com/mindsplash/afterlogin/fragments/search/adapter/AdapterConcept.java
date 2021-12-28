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
import com.mindsplash.network.model.Chapter;
import com.mindsplash.network.model.Concept;
import com.mindsplash.network.model.ConceptListData;

import java.util.ArrayList;

public class AdapterConcept extends RecyclerView.Adapter<AdapterConcept.GoogleView>{

    private ArrayList<Concept> searchList;
    private Context context;


    public AdapterConcept(ArrayList<Concept> searchList, Context context)  {
        this.searchList = searchList;
        this.context = context;

    }

    @NonNull
    @Override
    public AdapterConcept.GoogleView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comcept,parent,false);
        return new AdapterConcept.GoogleView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterConcept.GoogleView holder, int position) {
        Concept item = searchList.get(position);
//        holder.txtTitle.setText(item.getDefinitions().get(0));
//        holder.txtTheorm.setText(item.getTheorems().get(0));
//        holder.txtFormula.setText(item.getFormulae().get(0));
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public class GoogleView extends RecyclerView.ViewHolder{

        private TextView txtTitle,txtTheorm,txtFormula;
        private LinearLayout llView;

        public GoogleView(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.tv_definition);
            txtTheorm = itemView.findViewById(R.id.tv_theorms);
            txtFormula = itemView.findViewById(R.id.tv_formulae);


        }

    }
}

