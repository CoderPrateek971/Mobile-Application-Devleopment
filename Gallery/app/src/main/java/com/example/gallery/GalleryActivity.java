package com.example.gallery;


import android.os.Bundle;
import android.net.Uri;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.documentfile.provider.DocumentFile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        imageList = new ArrayList<>();
        adapter = new ImageAdapter(this, imageList);
        recyclerView.setAdapter(adapter);
        String uriString = getIntent().getStringExtra("folderUri");

        if (uriString != null) {
            folderUri = Uri.parse(uriString);
        }

        String folderName = "Pixel Gallery";

        if (folderUri != null) {
            DocumentFile folder = DocumentFile.fromTreeUri(this, folderUri);
            if (folder != null && folder.getName() != null) {
                folderName = folder.getName();
            }
        }
        getSupportActionBar().setTitle(folderName);


    }
    private void loadImages() {

        imageList.clear();


        if (folderUri == null) {
            return;
        }
        try {
            DocumentFile folder = DocumentFile.fromTreeUri(this, folderUri);

            if (folder != null && folder.isDirectory()) {

                DocumentFile[] files = folder.listFiles();

                if (files != null) {
                    for (DocumentFile file : files) {
                        String name = file.getName();
                        if (name != null) {
                            String lowerName = name.toLowerCase();
                            if (lowerName.endsWith(".jpg") ||
                                    lowerName.endsWith(".png") ||
                                    lowerName.endsWith(".jpeg")) {

                                imageList.add(file.getUri());
                            }
                        }
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Cannot read this folder. Try a different one.", Toast.LENGTH_LONG).show();
            finish();
        }
        adapter.notifyDataSetChanged();


    }

    @Override
    protected void onResume() {
        super.onResume();
        loadImages();
    }
}