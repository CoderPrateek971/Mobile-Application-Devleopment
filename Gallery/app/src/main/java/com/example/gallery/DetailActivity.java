package com.example.gallery;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.documentfile.provider.DocumentFile;



public class DetailActivity extends AppCompatActivity {
    ImageView imageView;
    TextView detailsText;
    Button deleteBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toast.makeText(this, "My Activity Opened", Toast.LENGTH_SHORT).show();

        imageView = findViewById(R.id.imageView);
        detailsText = findViewById(R.id.detailsText);
        deleteBtn = findViewById(R.id.deleteBtn);

        String uriString = getIntent().getStringExtra("path");
        Uri uri = Uri.parse(uriString);

        imageView.setImageURI(uri);

        String details = "URI: " + uri.toString();

        detailsText.setText(details);

        deleteBtn.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setMessage("Delete this image?")
                    .setPositiveButton("Yes", (d, w) -> {

                        DocumentFile documentFile =
                                DocumentFile.fromSingleUri(this, uri);

                        if (documentFile != null && documentFile.delete()) {
                            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(this, "Delete failed", Toast.LENGTH_SHORT).show();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }
}