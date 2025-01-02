package com.example.quizapp.Adapter;

import static com.example.quizapp.Activity.CourseHomeActivity.COURSE_ABBREVIATION_EXTRA;
import static com.example.quizapp.Activity.CourseHomeActivity.COURSE_NAME_EXTRA;
import static com.example.quizapp.Activity.CourseHomeActivity.COURSE_TYPE_EXTRA;

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
import com.example.quizapp.Activity.CourseHomeActivity;
import com.example.quizapp.Model.CourseDomain;
import com.example.quizapp.R;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.Viewholder> {

    ArrayList<CourseDomain> items;
    Context context;
    Animation translateAnim;

    public CourseAdapter(ArrayList<CourseDomain> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inFlator = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder, parent, false);
        context = parent.getContext();
        return new Viewholder(inFlator);
    }

    @Override

    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        CourseDomain course = items.get(position);
        holder.title.setText(items.get(position).getTitle());
        holder.code.setText(items.get(position).getCode());

        //modify below statement for retrieving images from drawable/course_icons folder
        //int drawableResourceId = holder.itemView.getResources().getIdentifier(items.get(position).getImgPath(), "drawable", holder.itemView.getContext().getPackageName());
        int drawableResourceId = holder.itemView.getResources().getIdentifier(items.get(position).getImgPath(), "drawable", holder.itemView.getContext().getPackageName());


        Glide.with(context)
                .load(drawableResourceId)
                .into(holder.courseImg);

        switch ((position%4)) {
            case 0:
                holder.courseBg.setImageResource(R.drawable.bg_1);
                holder.layout.setBackgroundResource(R.drawable.list_background_1);
                break;
            case 1:
                holder.courseBg.setImageResource(R.drawable.bg_2);
                holder.layout.setBackgroundResource(R.drawable.list_background_2);
                break;
            case 2:
                holder.courseBg.setImageResource(R.drawable.bg_3);
                holder.layout.setBackgroundResource(R.drawable.list_background_3);
                break;
            case 3:
                holder.courseBg.setImageResource(R.drawable.bg_4);
                holder.layout.setBackgroundResource(R.drawable.list_background_4);
                break;
            default:
                holder.courseBg.setImageResource(R.drawable.bg_1);
                holder.layout.setBackgroundResource(R.drawable.list_background_1);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(context, CourseHomeActivity.class);
               intent.putExtra(COURSE_NAME_EXTRA,course.getTitle());
               intent.putExtra(COURSE_ABBREVIATION_EXTRA,course.getImgPath());
               intent.putExtra(COURSE_TYPE_EXTRA,course.getType());
               context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView title, code;
        ImageView courseImg, courseBg;
        ConstraintLayout layout;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.courseTitle);
            code = itemView.findViewById(R.id.courseCode);
            courseBg = itemView.findViewById(R.id.courseBg);
            courseImg = itemView.findViewById(R.id.courseImg);
            layout = itemView.findViewById(R.id.item_layout);
            translateAnim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            layout.setAnimation(translateAnim);
        }
    }
}
