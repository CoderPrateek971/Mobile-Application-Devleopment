package com.example.gallery;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {
    ImageView imageView;
    TextView detailsText;
    Button deleteBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageView = findViewById(R.id.imageView);
        detailsText = findViewById(R.id.detailsText);
        deleteBtn = findViewById(R.id.deleteBtn);

        String path = getIntent().getStringExtra("path");

        File file = new File(path);

        imageView.setImageURI(Uri.fromFile(file));

        String details =
                "Name: " + file.getName() +
                        "\nPath: " + path +
                        "\nSize: " + file.length() +
                        "\nDate: " + new Date(file.lastModified());

        detailsText.setText(details);

        deleteBtn.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setMessage("Delete this image?")
                    .setPositiveButton("Yes", (d, w) -> {
                        file.delete();
                        finish(); // go back
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }
}