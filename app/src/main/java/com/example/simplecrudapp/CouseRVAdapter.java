package com.example.simplecrudapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CouseRVAdapter extends RecyclerView.Adapter<CouseRVAdapter.ViewHolder>{
    private ArrayList<CourseRVModel> model;
    private Context context;
    int lastPost = -1;
    private CourseClickInterface courseClickInterface;

    public CouseRVAdapter(ArrayList<CourseRVModel> model, Context context, CourseClickInterface courseClickInterface) {
        this.model = model;
        this.context = context;
        this.courseClickInterface = courseClickInterface;
    }

    @NonNull
    @Override
    public CouseRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.course_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CouseRVAdapter.ViewHolder holder, int position) {
        CourseRVModel selectedItem = model.get(position);
        holder.coursename.setText(selectedItem.getCoursename());
        holder.courseprice.setText(selectedItem.getCourseprice());
        Picasso.get().load(selectedItem.getCourseimg()).into(holder.courseimg);
        setAnimation(holder.itemView,position);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                courseClickInterface.onCourseClick(position);
            }
        });
    }

    private void setAnimation(View itemView, int Position){
        if(Position>lastPost){
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPost = Position;
        }
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView coursename, courseprice;
        private ImageView courseimg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            coursename = itemView.findViewById(R.id.idTVname);
            courseprice = itemView.findViewById(R.id.idTVPrice);
            courseimg = itemView.findViewById(R.id.idRVcourseItem);
        }
    }

    public interface CourseClickInterface{
        void onCourseClick(int position);
    }
}
