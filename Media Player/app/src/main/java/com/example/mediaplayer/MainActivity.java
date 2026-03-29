package com.example.mediaplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_FILE = 1;

    VideoView videoView;
    MediaPlayer mediaPlayer;

    Button openFile, openUrl, play, pause, stop, restart;
    EditText urlInput;

    Uri mediaUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = findViewById(R.id.videoView);
        openFile = findViewById(R.id.openFile);
        openUrl = findViewById(R.id.openUrl);
        play = findViewById(R.id.play);
        pause = findViewById(R.id.pause);
        stop = findViewById(R.id.stop);
        restart = findViewById(R.id.restart);
        urlInput = findViewById(R.id.urlInput);

        openFile.setOnClickListener(v -> openFile());
        openUrl.setOnClickListener(v -> loadUrl());
        play.setOnClickListener(v -> playMedia());
        pause.setOnClickListener(v -> pauseMedia());
        stop.setOnClickListener(v -> stopMedia());
        restart.setOnClickListener(v -> restartMedia());
    }

    private void openFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, PICK_FILE);
    }

    private void loadUrl() {
        String url = urlInput.getText().toString();

        if (url.isEmpty()) return;

        mediaUri = Uri.parse(url);
        videoView.setVideoURI(mediaUri);
    }

    private void playMedia() {
        if (mediaUri == null) return;

        videoView.start();
    }

    private void pauseMedia() {
        if (videoView.isPlaying()) {
            videoView.pause();
        }
    }

    private void stopMedia() {
        videoView.stopPlayback();
    }

    private void restartMedia() {
        if (mediaUri != null) {
            videoView.setVideoURI(mediaUri);
            videoView.start();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE && resultCode == RESULT_OK) {
            mediaUri = data.getData();

            videoView.setVideoURI(mediaUri);
        }
    }



}