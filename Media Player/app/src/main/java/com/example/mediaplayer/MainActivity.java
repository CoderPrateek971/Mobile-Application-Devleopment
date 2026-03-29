package com.example.mediaplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Toast;
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
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

    }

    private void openFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, PICK_FILE);
    }

    private void loadUrl() {
        String url = urlInput.getText().toString().trim();

        if (url.isEmpty()) {
            urlInput.setError("Enter URL");
            return;
        }
        Log.d("VIDEO_URL", url);
        mediaUri = Uri.parse(url);

        videoView.setVideoURI(mediaUri);

        videoView.setOnPreparedListener(mp -> {
            videoView.start();
        });

        videoView.setOnErrorListener((mp, what, extra) -> {
            Toast.makeText(this, "Video failed to load", Toast.LENGTH_SHORT).show();
            return true;
        });
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
        videoView.pause();
        videoView.seekTo(0);
    }

    private void restartMedia() {
        if (mediaUri != null) {
            videoView.seekTo(0);
            videoView.start();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE && resultCode == RESULT_OK) {
            mediaUri = data.getData();

            videoView.setVideoURI(mediaUri);
            videoView.start();
        }
    }



}