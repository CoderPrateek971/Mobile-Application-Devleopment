package com.example.gallery;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder>{
        Context context;
        ArrayList<Uri> images;

        public ImageAdapter(Context context, ArrayList<Uri> images) {
            this.context = context;
            this.images = images;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.item_image, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            Uri uri = images.get(position);
            holder.imageView.setImageURI(null);
            holder.imageView.setImageURI(uri);
            holder.imageView.setClickable(false);
            holder.imageView.setFocusable(false);
            holder.itemView.setClickable(true);

            holder.itemView.setOnClickListener(v -> {
                Toast.makeText(context, "Clicked!", Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "ITEM CLICK", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("path", uri.toString());
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                context.startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return images.size();
        }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }





}
