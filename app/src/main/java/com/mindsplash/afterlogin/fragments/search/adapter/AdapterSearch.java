package com.mindsplash.afterlogin.fragments.search.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.judemanutd.katexview.KatexView;
import com.mindsplash.R;
import com.mindsplash.afterlogin.common.FetchImageUrl;
import com.mindsplash.afterlogin.fragments.search.beans.Item;
import com.mindsplash.network.model.Chapter;
import com.mindsplash.network.model.Question;
import com.mindsplash.network.model.SearchData;

import java.util.ArrayList;

public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.GoogleView> {

    private ArrayList<Question> searchList;
    private Context context;
    int ScreenW, ScreenH;


    public AdapterSearch(ArrayList<Question> searchList, Context context) {
        this.searchList = searchList;
        this.context = context;
    }

    @NonNull
    @Override
    public GoogleView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels;
        float dpHeight = displayMetrics.heightPixels;
        ScreenW = (int) dpWidth;
        ScreenH = (int) dpHeight;
        return new AdapterSearch.GoogleView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoogleView holder, int position) {
        Question item = searchList.get(position);
        holder.txtTitle.setText(Html.fromHtml((position + 1) + ". " + item.question, Images, null));
        if (!item.option_a.contains("[latex]")) {
            holder.tvOptionA.setText(Html.fromHtml("a. " + item.option_a, Images, null));
            holder.tvOptionAltx.setVisibility(View.GONE);
            holder.tvOptionA.setVisibility(View.VISIBLE);
        } else {
            holder.tvOptionAltx.setVisibility(View.VISIBLE);
            holder.tvOptionA.setVisibility(View.GONE);
            holder.tvOptionAltx.setText("a. $$ " + item.option_a.replace("[/latex]", "").replace("[latex]", "") + " $$");

        }

        if (!item.option_b.contains("[latex]")) {
            holder.tvOptionB.setText(Html.fromHtml("b. " + item.option_b, Images, null));
            holder.tvOptionBltx.setVisibility(View.GONE);
            holder.tvOptionB.setVisibility(View.VISIBLE);
        } else {
            holder.tvOptionBltx.setText("b. $$ " + item.option_b.replace("[/latex]", "").replace("[latex]", "") + " $$");
            holder.tvOptionBltx.setVisibility(View.VISIBLE);
            holder.tvOptionB.setVisibility(View.GONE);
        }
        if (!item.option_c.contains("[latex]")) {
            holder.tvOptionC.setText(Html.fromHtml("c. " + item.option_c, Images, null));

            holder.tvOptionCltx.setVisibility(View.GONE);
            holder.tvOptionC.setVisibility(View.VISIBLE);
        } else {
            holder.tvOptionCltx.setVisibility(View.VISIBLE);
            holder.tvOptionC.setVisibility(View.GONE);
            holder.tvOptionCltx.setText("c. $$ " + item.option_c.replace("[/latex]", "").replace("[latex]", "") + " $$");

        }

        if (!item.option_d.contains("[latex]")) {
            holder.tvOptionDltx.setVisibility(View.GONE);
            holder.tvOptionD.setVisibility(View.VISIBLE);
            holder.tvOptionD.setText(Html.fromHtml("d. " + item.option_d, Images, null));
        } else {
            holder.tvOptionDltx.setVisibility(View.VISIBLE);
            holder.tvOptionD.setVisibility(View.GONE);
            holder.tvOptionDltx.setText("d. $$ " + item.option_d.replace("[/latex]", "").replace("[latex]", "") + " $$");

        }

        holder.tvAnswer.setText("Answer: " + Html.fromHtml(item.correct_answer));
///&& item.question_solution.contains("<img>")
        if (!item.question_solution.contains("latex")) {
            holder.tvSolution.setText(Html.fromHtml("Solution: " + item.question_solution, Images, null));

        } else {
            holder.tv_solution_ltx.setText("Solution: $$ " + item.question_solution.replace("[/latex]", "").replace("[latex]", "") + " $$");

        }

    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    private Html.ImageGetter Images = new Html.ImageGetter() {

        public Drawable getDrawable(String source) {
            Drawable drawable = null;
            FetchImageUrl fiu = new FetchImageUrl(context, source);
            try {
                fiu.execute().get();
                drawable = fiu.GetImage();
            } catch (Exception e) {
                drawable = context.getResources().getDrawable(R.drawable.back_circle_solid);
            }
            // to display image,center of screen
            int imgH = 150;
            int imgW = 150;
            int padding = 20;
            int realWidth = ScreenW - (2 * padding);
            int realHeight = imgH * realWidth / imgW;
            drawable.setBounds(padding, 0, 200, 200);
            return drawable;
        }
    };

    public class GoogleView extends RecyclerView.ViewHolder {

        private TextView txtTitle, txtDesc, tvOptionA, tvOptionB, tvOptionC, tvOptionD, tvAnswer, tvSolution;
        private RelativeLayout llView;
        private KatexView tvOptionAltx, tvOptionBltx, tvOptionDltx, tvOptionCltx, tv_solution_ltx;

        public GoogleView(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.tv_question);
            tvOptionA = itemView.findViewById(R.id.tv_option_a);
            tvOptionB = itemView.findViewById(R.id.tv_option_b);
            tvOptionC = itemView.findViewById(R.id.tv_option_c);
            tvOptionD = itemView.findViewById(R.id.tv_option_d);
            tvOptionAltx = itemView.findViewById(R.id.tv_option_a_ltx);
            tvOptionBltx = itemView.findViewById(R.id.tv_option_b_ltx);
            tvOptionCltx = itemView.findViewById(R.id.tv_option_c_ltx);
            tvOptionDltx = itemView.findViewById(R.id.tv_option_d_ltx);
            tv_solution_ltx = itemView.findViewById(R.id.tv_solution_ltx);

            tvAnswer = itemView.findViewById(R.id.tv_answer);
            tvSolution = itemView.findViewById(R.id.tv_solution);

        }

    }
}

