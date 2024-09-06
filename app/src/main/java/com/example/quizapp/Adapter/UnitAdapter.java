package com.example.quizapp.Adapter;

import static com.example.quizapp.Activity.PdfViewActivity.PDF_RESOURCE_ID_EXTRA;
import static com.example.quizapp.Activity.PdfViewActivity.PDF_SUB_TITLE_EXTRA;
import static com.example.quizapp.Activity.PdfViewActivity.PDF_TITLE_EXTRA;
import static com.example.quizapp.Activity.PdfViewActivity.PDF_URL_EXTRA;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quizapp.Activity.PdfViewActivity;
import com.example.quizapp.Activity.QuizActivity;
import com.example.quizapp.SingletonClasses.PdfDataManager;
import com.example.quizapp.Domain.Units;
import com.example.quizapp.R;

import java.util.ArrayList;

public class UnitAdapter extends RecyclerView.Adapter<UnitAdapter.viewholder> {

    ArrayList<Units> unitsList = new ArrayList<>();
    Context context;
    String course;
    Animation translateAnim;

    public UnitAdapter(ArrayList<Units> unitsList, String course, Context context) {
        this.unitsList = unitsList;
        this.course = course;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inFlator = LayoutInflater.from(context).inflate(R.layout.unit_item, parent, false);
        return new viewholder(inFlator);
    }
    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull viewholder holder,int position) {
        holder.unitTitle.setText(unitsList.get(position).getUnitName());
        String weightStr = Integer.toString(unitsList.get(position).getWeight());
        holder.weight.setText(weightStr);

        int drawableResourceId = holder.itemView.getResources().getIdentifier(course, "drawable", holder.itemView.getContext().getPackageName());


        Glide.with(context)
                .load(drawableResourceId)
                .into(holder.unitImg);

        switch ((position % 4)) {
            case 0:
                holder.unitBg.setImageResource(R.drawable.bg_1);
                holder.layout.setBackgroundResource(R.drawable.list_background_1);
                break;
            case 1:
                holder.unitBg.setImageResource(R.drawable.bg_2);
                holder.layout.setBackgroundResource(R.drawable.list_background_2);
                break;
            case 2:
                holder.unitBg.setImageResource(R.drawable.bg_3);
                holder.layout.setBackgroundResource(R.drawable.list_background_3);
                break;
            case 3:
                holder.unitBg.setImageResource(R.drawable.bg_4);
                holder.layout.setBackgroundResource(R.drawable.list_background_4);
                break;
            default:
                holder.unitBg.setImageResource(R.drawable.bg_1);
                holder.layout.setBackgroundResource(R.drawable.list_background_1);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (course.equals("ajp") || course.equals("est")) {
                    Intent intent = new Intent(context, QuizActivity.class);
                    intent.putExtra("course", course);
                    intent.putExtra("unitId", unitsList.get(position).getUnitNo());
                    intent.putExtra("unitName", unitsList.get(position).getUnitName());
                    intent.putExtra("weight", weightStr);
                    intent.putExtra("courseImgResource", drawableResourceId);
                    context.startActivity(intent);
                }
                else {
                    Intent intent = new Intent(context, PdfViewActivity.class);
                    intent.putExtra(PDF_URL_EXTRA,PdfDataManager.getInstance().getPdfUrl(course,unitsList.get(position).getUnitNo()));
                    intent.putExtra(PDF_TITLE_EXTRA,unitsList.get(position).getUnitName());
                    intent.putExtra(PDF_SUB_TITLE_EXTRA,weightStr);
                    intent.putExtra(PDF_RESOURCE_ID_EXTRA,course);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return unitsList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        TextView unitTitle, weight;
        ImageView unitImg, unitBg;
        ConstraintLayout layout;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            unitTitle = itemView.findViewById(R.id.unitTitle);
            weight = itemView.findViewById(R.id.unitWeight);
            unitImg = itemView.findViewById(R.id.unitImg);
            unitBg = itemView.findViewById(R.id.unitBg);
            layout = itemView.findViewById(R.id.unit_layout);
            translateAnim = AnimationUtils.loadAnimation(context,R.anim.translate_anim);
            layout.setAnimation(translateAnim);
        }
    }
}
