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
import com.mindsplash.network.model.OlympiadQues;
import com.mindsplash.network.model.Quation;

import java.util.ArrayList;

public class AdapterOlympiadQuestion extends RecyclerView.Adapter<AdapterOlympiadQuestion.OlympiadQueViewHolder> {
    private final ArrayList<OlympiadQues> quesArrayList;
    private final Context context;
    private QuationAnswserSelection quationAnswserSelection;
    private SubmitAnswer submitAnswer;
    private String quationId;
    private boolean isCorrect;
    private int answeredPosition;
    private int currentPosition;

    public AdapterOlympiadQuestion(ArrayList<OlympiadQues> quesArrayList, Context context) {
        this.quesArrayList = quesArrayList;
        this.context = context;
    }

    public void onAnsSubmited(int answeredPosition, String quationId, boolean isCorrect) {
        this.answeredPosition = answeredPosition;
        this.quationId = quationId;
        this.isCorrect = isCorrect;
    }

    public void setQuationAnswserSelection(QuationAnswserSelection quationAnswserSelection) {
        this.quationAnswserSelection = quationAnswserSelection;
    }

    @NonNull
    @Override
    public OlympiadQueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_olympiad_question, parent, false);
        return new OlympiadQueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OlympiadQueViewHolder holder, int position) {
        String[] choices = quesArrayList.get(position).getChoices().split(",");
        holder.txtQuestion.setText(quesArrayList.get(position).getQuestion());
        holder.txtQuestionNo.setText("Question " + position);

        holder.txtAns1.setText(choices[0]);
        holder.txtAns2.setText(choices[1]);
        holder.txtAns3.setText(choices[2]);
        holder.txtAns4.setText(choices[3]);

        if (quationId != null) {
            String[] answ = quesArrayList.get(position).getChoices().split(",");
            String correctChoice = quesArrayList.get(position).getCorrect_choice();

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
            }

            for (int i = 1; i <= answ.length; i++) {
                if (correctChoice.trim().equalsIgnoreCase(answ[i-1].trim())) {
                    switch (i) {
                        case 1:
                            holder.clOpt1.setBackground(context.getResources().getDrawable(R.drawable.back_correct_answ));
                            holder.txtA.setBackground(context.getResources().getDrawable(R.drawable.back_rounded_green));
                            break;
                        case 2:
                            holder.clOpt2.setBackground(context.getResources().getDrawable(R.drawable.back_correct_answ));
                            holder.txtB.setBackground(context.getResources().getDrawable(R.drawable.back_rounded_green));
                            break;
                        case 3:
                            holder.clOPt3.setBackground(context.getResources().getDrawable(R.drawable.back_correct_answ));
                            holder.txtC.setBackground(context.getResources().getDrawable(R.drawable.back_rounded_green));
                            break;
                        case 4:
                            holder.clOpt4.setBackground(context.getResources().getDrawable(R.drawable.back_correct_answ));
                            holder.txtD.setBackground(context.getResources().getDrawable(R.drawable.back_rounded_green));
                            break;
                    }
                }
            }
        } else
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

    public void notifyItemChange(String quationId) {
        for (int i = 0; i < quesArrayList.size(); i++) {
            if (quesArrayList.get(i).getOq_id().equalsIgnoreCase(quationId)) {
                notifyItemChanged(i);
                break;
            }
        }
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public class OlympiadQueViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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


        public OlympiadQueViewHolder(@NonNull View itemView) {
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
                    quationAnswserSelection.onAnswerSelected(1, quesArrayList.get(getAdapterPosition()).getOq_id(),
                            txtAns1.getText().toString().trim().equalsIgnoreCase(quesArrayList.get(getAdapterPosition()).getCorrect_choice().trim()));
                    break;
                case R.id.opt_two:
                    clOpt1.setBackground(context.getResources().getDrawable(R.drawable.back_strock_option_null));
                    clOpt2.setBackground(context.getResources().getDrawable(R.drawable.back_strock_option));
                    clOPt3.setBackground(context.getResources().getDrawable(R.drawable.back_strock_option_null));
                    clOpt4.setBackground(context.getResources().getDrawable(R.drawable.back_strock_option_null));
                    quationAnswserSelection.onAnswerSelected(2, quesArrayList.get(getAdapterPosition()).getOq_id(),
                            txtAns2.getText().toString().trim().equalsIgnoreCase(quesArrayList.get(getAdapterPosition()).getCorrect_choice().trim()));
                    break;
                case R.id.opt_three:
                    clOpt1.setBackground(context.getResources().getDrawable(R.drawable.back_strock_option_null));
                    clOpt2.setBackground(context.getResources().getDrawable(R.drawable.back_strock_option_null));
                    clOPt3.setBackground(context.getResources().getDrawable(R.drawable.back_strock_option));
                    clOpt4.setBackground(context.getResources().getDrawable(R.drawable.back_strock_option_null));
                    quationAnswserSelection.onAnswerSelected(3, quesArrayList.get(getAdapterPosition()).getOq_id(),
                            txtAns3.getText().toString().trim().equalsIgnoreCase(quesArrayList.get(getAdapterPosition()).getCorrect_choice().trim()));
                    break;
                case R.id.opt_four:
                    clOpt1.setBackground(context.getResources().getDrawable(R.drawable.back_strock_option_null));
                    clOpt2.setBackground(context.getResources().getDrawable(R.drawable.back_strock_option_null));
                    clOPt3.setBackground(context.getResources().getDrawable(R.drawable.back_strock_option_null));
                    clOpt4.setBackground(context.getResources().getDrawable(R.drawable.back_strock_option));
                    quationAnswserSelection.onAnswerSelected(4, quesArrayList.get(getAdapterPosition()).getOq_id(),
                            txtAns4.getText().toString().trim().equalsIgnoreCase(quesArrayList.get(getAdapterPosition()).getCorrect_choice().trim()));
                    break;
            }
        }
    }
}
