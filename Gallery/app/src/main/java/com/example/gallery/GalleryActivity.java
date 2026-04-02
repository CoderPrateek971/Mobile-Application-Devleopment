package com.example.gallery;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.net.Uri;
import androidx.documentfile.provider.DocumentFile;

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
    ArrayList<Uri> imageList;
    ImageAdapter adapter;
    Uri folderUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        imageList = new ArrayList<>();
        adapter = new ImageAdapter(this, imageList);
        recyclerView.setAdapter(adapter);
        String uriString = getIntent().getStringExtra("folderUri");

        if (uriString != null) {
            folderUri = Uri.parse(uriString);
        }
        loadImages();


    }
    private void loadImages() {

        imageList.clear();


        if (folderUri == null) {
            return;
        }
        DocumentFile folder = DocumentFile.fromTreeUri(this, folderUri);

        if (folder != null && folder.isDirectory()) {
            for (DocumentFile file : folder.listFiles()) {

                String name = file.getName();

                if (name != null &&
                        (name.endsWith(".jpg") ||
                                name.endsWith(".png") ||
                                name.endsWith(".jpeg"))) {

                    imageList.add(file.getUri());
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