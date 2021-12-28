package com.mindsplash.afterlogin.fragments.search.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mindsplash.R;
import com.mindsplash.afterlogin.common.learn.activities.ChapterActivity;
import com.mindsplash.helper.Constants;
import com.mindsplash.network.model.Subject;

import java.util.List;

public class AdapterSubjects extends RecyclerView.Adapter<AdapterSubjects.SubjectViewHolder> {
    private final List<Subject> subjectList;
    private final Context context;
    private final String from;

    public AdapterSubjects(Context context, List<Subject> subjectList, String from) {
        this.subjectList = subjectList;
        this.from = from;
        this.context = context;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_subject_layout, parent, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        Subject subject = subjectList.get(position);
        holder.txtSubjectName.setText(subject.getSubjectname());
        if (subject.getSubjectname().contains("math") || subject.getSubjectname().contains("Math"))
            holder.imgSubject.setImageDrawable(context.getDrawable(R.drawable.maths));
        else if (subject.getSubjectname().contains("phy") || subject.getSubjectname().contains("Phy"))
            holder.imgSubject.setImageDrawable(context.getDrawable(R.drawable.physics));
        else if (subject.getSubjectname().contains("che") || subject.getSubjectname().contains("Che"))
            holder.imgSubject.setImageDrawable(context.getDrawable(R.drawable.chemistry));
        else if (subject.getSubjectname().contains("bio") || subject.getSubjectname().contains("Bio"))
            holder.imgSubject.setImageDrawable(context.getDrawable(R.drawable.biology));
        else
            holder.imgSubject.setImageDrawable(context.getDrawable(R.drawable.logo));

    }

    @Override
    public int getItemCount() {
        if (subjectList != null)
            return subjectList.size();
        else
            return 0;
    }

    public class SubjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView txtSubjectName;
        private final ImageView imgSubject;

        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSubjectName = itemView.findViewById(R.id.txt_subject_name);
            imgSubject = itemView.findViewById(R.id.img_subject);
            imgSubject.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_subject:
                    Intent intent = new Intent(context, ChapterActivity.class);
                    intent.putExtra(Constants.SUBJ_ID, subjectList.get(getAdapterPosition()).getSid());
                    intent.putExtra(Constants.SUBJ_NAME, subjectList.get(getAdapterPosition()).getSubjectname());
                    context.startActivity(intent);
                    break;
            }
        }
    }
}
