package com.example.adminpanel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<orderdibo> list;

    public MyAdapter(Context context, ArrayList<orderdibo> list) {
        this.context = context;
        this.list = list;
    }

    //search option optimization:

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.sample, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        orderdibo u = list.get(position);

        holder.txt1.setText(u.getphone());
        Glide.with(context)
                .load(u.getprofileimage())
                .into(holder.imageView);

        //Showing item's all info in a large screen of next page:

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView,imageView1,imageView2,imageView3;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt1 = itemView.findViewById(R.id.textView1);
            txt2 = itemView.findViewById(R.id.textView2);
            txt3 = itemView.findViewById(R.id.textView3);
            txt4 = itemView.findViewById(R.id.textView4);
            imageView = itemView.findViewById(R.id.imageView3);
            imageView1 = itemView.findViewById(R.id.imageView1);
            imageView2 = itemView.findViewById(R.id.imageView2);

            imageView3 = itemView.findViewById(R.id.imageView4);


        }
    }
}

