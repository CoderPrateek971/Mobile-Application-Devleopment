package com.example.gallery;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE = 1;
    Uri imageUri;
    String currentPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.openGalleryBtn);

        btn.setOnClickListener(v -> {
            startActivity(new Intent(this, GalleryActivity.class));
        });
    }

    public void takePhoto(View view) throws IOException {
        try {
            File file = createImageFile();

            imageUri = FileProvider.getUriForFile(this,
                    getPackageName() + ".provider", file);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_IMAGE);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private File createImageFile() throws IOException {

        String name = "IMG_" + System.currentTimeMillis();

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(name, ".jpg", storageDir);

        currentPath = image.getAbsolutePath();

        return image;
    }




}