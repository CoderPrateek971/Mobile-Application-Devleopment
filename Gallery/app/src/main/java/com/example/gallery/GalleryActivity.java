package com.example.gallery;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<File> imageList;
    ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        imageList = new ArrayList<>();
        adapter = new ImageAdapter(this, imageList);
        recyclerView.setAdapter(adapter);
        loadImages();


    }
    private void loadImages() {

        File folder = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (folder == null) {
            return;
        }
        File[] files = folder.listFiles();
        imageList.clear();

        Log.d("DEBUG", "Folder: " + folder.getAbsolutePath());
        if (files != null) {
            for (File file : files) {
                if (file.getName().endsWith(".jpg")) {
                    imageList.add(file);
                }
            }
        }
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadImages();
    }
}