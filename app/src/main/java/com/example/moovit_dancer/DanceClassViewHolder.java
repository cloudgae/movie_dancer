package com.example.moovit_dancer;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DanceClassViewHolder extends RecyclerView.ViewHolder {
    public TextView classname, mozip /*classdate*/;
    ImageView classimg;

    public DanceClassViewHolder(@NonNull View itemView) {
        super(itemView);
        classname = itemView.findViewById(R.id.classname);
        mozip = itemView.findViewById(R.id.mozip);
//        classdate = itemView.findViewById(R.id.classdate);
        classimg = itemView.findViewById(R.id.classimg);
    }
}
