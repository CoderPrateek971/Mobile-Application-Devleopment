package com.example.gallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.Manifest;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_FOLDER = 200;
    static final int REQUEST_IMAGE = 100;
    Uri imageUri;
    String currentPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.openFolderBtn);

        btn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
            startActivityForResult(intent, REQUEST_FOLDER);
        });
    }

    public void takePhoto(View view) throws IOException {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, 100);
            return;
        }

        try {
            File file = createImageFile();

            imageUri = FileProvider.getUriForFile(this,
                    getPackageName() + ".provider", file);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_IMAGE);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error creating image file", Toast.LENGTH_SHORT).show();
        }
    }


    private File createImageFile() throws IOException {

        String name = "IMG_" + System.currentTimeMillis();

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(name, ".jpg", storageDir);

        currentPath = image.getAbsolutePath();

        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {

            File file = new File(currentPath);

            if (file.length() == 0 && data != null) {
                try {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");

                    FileOutputStream fos = new FileOutputStream(file);
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (requestCode == REQUEST_FOLDER && resultCode == RESULT_OK) {

                Uri treeUri = data.getData();

                Intent intent = new Intent(this, GalleryActivity.class);
                intent.putExtra("folderUri", treeUri.toString());

                startActivity(intent);
            }
        }
    }
}




