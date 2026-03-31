package com.example.gallery;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder>{
        Context context;
        ArrayList<File> images;

        public ImageAdapter(Context context, ArrayList<File> images) {
            this.context = context;
            this.images = images;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.item_image, parent, false);
            return new ViewHolder(view);   // ✅ FIXED
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            File file = images.get(position);
            holder.imageView.setImageURI(Uri.fromFile(file));

            holder.imageView.setOnClickListener(v -> {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("path", file.getAbsolutePath());
                context.startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return images.size();
        }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }





}
