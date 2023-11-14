package com.example.moovit_dancer;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.exoplayer2.upstream.DataSource;

import java.util.List;

public class DanceClassAdapter extends RecyclerView.Adapter<DanceClassViewHolder> {

    private List<DanceClass> classes;

    public DanceClassAdapter(List<DanceClass> classes) {
        this.classes = classes;
    }

    @NonNull
    @Override
    public DanceClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dance_class, parent, false);
        return new DanceClassViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DanceClassViewHolder holder, int position) {
        DanceClass danceClass = classes.get(position);
        holder.classname.setText(danceClass.getClassName());
        holder.mozip.setText(danceClass.getMozip());
//        holder.classdate.setText(danceClass.getClassDate());

        String image = "C7image";
        // Glide로 이미지 설정
        Glide.with(holder.itemView.getContext())
                .load("https://moovitbucket2.s3.amazonaws.com/" + image)
                .into(holder.classimg);
    }

    @Override
    public int getItemCount() {
        return classes.size();
    }


}
