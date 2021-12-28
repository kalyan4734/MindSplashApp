package com.mindsplash.afterlogin.common.learn.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mindsplash.R;
import com.mindsplash.afterlogin.common.learn.quationsinterfaces.QuationAnswserSelection;
import com.mindsplash.afterlogin.common.learn.quationsinterfaces.SubmitAnswer;
import com.mindsplash.network.model.AllQuation;

import java.util.ArrayList;

public class AdapterAllQuations extends RecyclerView.Adapter<AdapterAllQuations.AllQuationViewHolder> {
    private final ArrayList<AllQuation> quesArrayList;
    private final Context context;
    private QuationAnswserSelection quationAnswserSelection;
    private SubmitAnswer submitAnswer;
    private String quationId;
    private boolean isCorrect;
    private int answeredPosition;
    private int currentPosition;
    String[] arr;

    public AdapterAllQuations(ArrayList<AllQuation> quesArrayList, Context context) {
        this.quesArrayList = quesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public AllQuationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_olympiad_question, parent, false);
        return new AllQuationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllQuationViewHolder holder, int position) {
        holder.txtQuestion.setText(quesArrayList.get(position).getQuestion());
        holder.txtQuestionNo.setText("Question " + position);

        holder.txtAns1.setText(quesArrayList.get(position).getOption_a());
        holder.txtAns2.setText(quesArrayList.get(position).getOption_b());
        holder.txtAns3.setText(quesArrayList.get(position).getOption_c());
        holder.txtAns4.setText(quesArrayList.get(position).getOption_d());

        if (quationId != null && quationId.trim().equalsIgnoreCase(quesArrayList.get(position).getId())) {
            String correctChoice = quesArrayList.get(position).getCorrect_answer();

            switch (answeredPosition) {
                case 1:
                    holder.clOpt1.setBackground(context.getResources().getDrawable(R.drawable.back_strock_option));
                    break;
                case 2:
                    holder.clOpt2.setBackground(context.getResources().getDrawable(R.drawable.back_strock_option));
                    break;
                case 3:
                    holder.clOPt3.setBackground(context.getResources().getDrawable(R.drawable.back_strock_option));
                    break;
                case 4:
                    holder.clOpt4.setBackground(context.getResources().getDrawable(R.drawable.back_strock_option));
                    break;
                default:
                    holder.clOpt1.setBackground(context.getResources().getDrawable(R.drawable.back_strock_option_null));
                    holder.clOpt2.setBackground(context.getResources().getDrawable(R.drawable.back_strock_option_null));
                    holder.clOPt3.setBackground(context.getResources().getDrawable(R.drawable.back_strock_option_null));
                    holder.clOpt4.setBackground(context.getResources().getDrawable(R.drawable.back_strock_option_null));
            }

            switch (correctChoice) {
                case "a":
                    holder.clOpt1.setBackground(context.getResources().getDrawable(R.drawable.back_correct_answ));
                    holder.txtA.setBackground(context.getResources().getDrawable(R.drawable.back_rounded_green));
                    break;
                case "b":
                    holder.clOpt2.setBackground(context.getResources().getDrawable(R.drawable.back_correct_answ));
                    holder.txtB.setBackground(context.getResources().getDrawable(R.drawable.back_rounded_green));
                    break;
                case "c":
                    holder.clOPt3.setBackground(context.getResources().getDrawable(R.drawable.back_correct_answ));
                    holder.txtC.setBackground(context.getResources().getDrawable(R.drawable.back_rounded_green));
                    break;
                case "d":
                    holder.clOpt4.setBackground(context.getResources().getDrawable(R.drawable.back_correct_answ));
                    holder.txtD.setBackground(context.getResources().getDrawable(R.drawable.back_rounded_green));
                    break;
            }

        }
        else
        {
            holder.clOpt1.setBackground(context.getResources().getDrawable(R.drawable.back_strock_option_null));
            holder.clOpt2.setBackground(context.getResources().getDrawable(R.drawable.back_strock_option_null));
            holder.clOPt3.setBackground(context.getResources().getDrawable(R.drawable.back_strock_option_null));
            holder.clOpt4.setBackground(context.getResources().getDrawable(R.drawable.back_strock_option_null));

            holder.txtA.setBackground(context.getResources().getDrawable(R.drawable.back_circle_option));
            holder.txtB.setBackground(context.getResources().getDrawable(R.drawable.back_circle_option));
            holder.txtC.setBackground(context.getResources().getDrawable(R.drawable.back_circle_option));
            holder.txtD.setBackground(context.getResources().getDrawable(R.drawable.back_circle_option));

        }
        currentPosition = holder.getAdapterPosition();
    }

    @Override
    public int getItemCount() {
        return quesArrayList.size();
    }

    public void onAnsSubmited(int answeredPosition, String quationId, boolean isCorrect) {
        this.answeredPosition = answeredPosition;
        this.quationId = quationId;
        this.isCorrect = isCorrect;
    }

    public void notifyItemChange(String quationId) {
        for (int i = 0; i < quesArrayList.size(); i++) {
            if (quesArrayList.get(i).getId().trim().equalsIgnoreCase(quationId.trim())) {
                notifyItemChanged(i);
                break;
            }
        }
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setQuationAnswserSelection(QuationAnswserSelection quationAnswserSelection) {
        this.quationAnswserSelection = quationAnswserSelection;
    }

    public class AllQuationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView txtQuestion;
        private final TextView txtQuestionNo;
        private final TextView txtAns1;
        private final TextView txtAns2;
        private final TextView txtAns3;
        private final TextView txtAns4;
        private final TextView txtA;
        private final TextView txtB;
        private final TextView txtC;
        private final TextView txtD;
        private final ConstraintLayout clOpt1;
        private final ConstraintLayout clOpt2;
        private final ConstraintLayout clOPt3;
        private final ConstraintLayout clOpt4;

        public AllQuationViewHolder(@NonNull View itemView) {
            super(itemView);
            txtQuestion = itemView.findViewById(R.id.txt_question);
            txtQuestionNo = itemView.findViewById(R.id.txt_question_no);

            txtAns1 = itemView.findViewById(R.id.ans_one);
            txtAns2 = itemView.findViewById(R.id.ans_two);
            txtAns3 = itemView.findViewById(R.id.ans_three);
            txtAns4 = itemView.findViewById(R.id.ans_four);

            txtA = itemView.findViewById(R.id.id_a);
            txtB = itemView.findViewById(R.id.id_b);
            txtC = itemView.findViewById(R.id.id_c);
            txtD = itemView.findViewById(R.id.id_d);

            clOpt1 = itemView.findViewById(R.id.opt_one);
            clOpt2 = itemView.findViewById(R.id.opt_two);
            clOPt3 = itemView.findViewById(R.id.opt_three);
            clOpt4 = itemView.findViewById(R.id.opt_four);

            clOpt1.setOnClickListener(this);
            clOpt2.setOnClickListener(this);
            clOPt3.setOnClickListener(this);
            clOpt4.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.opt_one:
                    clOpt1.setBackground(context.getResources().getDrawable(R.drawable.back_strock_option));
                    clOpt2.setBackground(context.getResources().getDrawable(R.drawable.back_strock_option_null));
                    clOPt3.setBackground(context.getResources().getDrawable(R.drawable.back_strock_option_null));
                    clOpt4.setBackground(context.getResources().getDrawable(R.drawable.back_strock_option_null));
                    quationAnswserSelection.onAnswerSelected(1, quesArrayList.get(getAdapterPosition()).getId(),
                            txtAns1.getText().toString().trim().equalsIgnoreCase(quesArrayList.get(getAdapterPosition()).getCorrect_answer().trim()));
                    break;
                case R.id.opt_two:
                    clOpt1.setBackground(context.getResources().getDrawable(R.drawable.back_strock_option_null));
                    clOpt2.setBackground(context.getResources().getDrawable(R.drawable.back_strock_option));
                    clOPt3.setBackground(context.getResources().getDrawable(R.drawable.back_strock_option_null));
                    clOpt4.setBackground(context.getResources().getDrawable(R.drawable.back_strock_option_null));
                    quationAnswserSelection.onAnswerSelected(2, quesArrayList.get(getAdapterPosition()).getId(),
                            txtAns2.getText().toString().trim().equalsIgnoreCase(quesArrayList.get(getAdapterPosition()).getCorrect_answer().trim()));
                    break;
                case R.id.opt_three:
                    clOpt1.setBackground(context.getResources().getDrawable(R.drawable.back_strock_option_null));
                    clOpt2.setBackground(context.getResources().getDrawable(R.drawable.back_strock_option_null));
                    clOPt3.setBackground(context.getResources().getDrawable(R.drawable.back_strock_option));
                    clOpt4.setBackground(context.getResources().getDrawable(R.drawable.back_strock_option_null));
                    quationAnswserSelection.onAnswerSelected(3, quesArrayList.get(getAdapterPosition()).getId(),
                            txtAns3.getText().toString().trim().equalsIgnoreCase(quesArrayList.get(getAdapterPosition()).getCorrect_answer().trim()));
                    break;
                case R.id.opt_four:
                    clOpt1.setBackground(context.getResources().getDrawable(R.drawable.back_strock_option_null));
                    clOpt2.setBackground(context.getResources().getDrawable(R.drawable.back_strock_option_null));
                    clOPt3.setBackground(context.getResources().getDrawable(R.drawable.back_strock_option_null));
                    clOpt4.setBackground(context.getResources().getDrawable(R.drawable.back_strock_option));
                    quationAnswserSelection.onAnswerSelected(4, quesArrayList.get(getAdapterPosition()).getId(),
                            txtAns4.getText().toString().trim().equalsIgnoreCase(quesArrayList.get(getAdapterPosition()).getCorrect_answer().trim()));
                    break;
            }
        }

    }
}
