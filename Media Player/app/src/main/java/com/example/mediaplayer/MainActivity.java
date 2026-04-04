package com.example.mediaplayer;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_FILE = 1;

    VideoView videoView;
    MediaPlayer mediaPlayer;

    Button openFile, openUrl, play, pause, stop, restart;
    EditText urlInput;

    Uri mediaUri;
    boolean isAudioPrepared = false;
    boolean isVideoPrepared = false;
    boolean isAudioPlayback = false;

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
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        videoView.setOnErrorListener((mp, what, extra) -> {
            Toast.makeText(this, "Video Error", Toast.LENGTH_SHORT).show();
            return true;
        });
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

        try {
            mediaUri = Uri.parse(url);

            String lower = url.toLowerCase();

            boolean isAudio = lower.endsWith(".mp3") || lower.endsWith(".wav") || lower.endsWith(".aac");

            playMediaUri(mediaUri, isAudio);

        } catch (Exception e) {
            Toast.makeText(this, "Invalid URL", Toast.LENGTH_SHORT).show();
        }
    }



    private void playMediaUri(Uri uri, boolean isAudio) {

        isAudioPlayback = isAudio;
        isAudioPrepared = false;
        isVideoPrepared = false;

        try {

            if (mediaPlayer != null) {
                mediaPlayer.release();
                mediaPlayer = null;
            }

            videoView.stopPlayback();

            if (isAudio) {

                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDataSource(uri.toString());
                mediaPlayer.prepareAsync();

                mediaPlayer.setOnPreparedListener(mp -> {
                    isAudioPrepared = true;
                    mediaPlayer.start();
                });

            } else {

                videoView.setVideoURI(Uri.parse(uri.toString()));
                videoView.requestFocus();

                videoView.setOnPreparedListener(mp -> {
                    isVideoPrepared = true;
                    videoView.start();
                });
            }

        } catch (Exception e) {
            Toast.makeText(this, "Playback Error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void playMedia() {
        if (isAudioPlayback && mediaPlayer != null && isAudioPrepared) {
            mediaPlayer.start();
        } else if (!isAudioPlayback && mediaUri != null && isVideoPrepared) {
            videoView.start();
        }
    }

    private void pauseMedia() {
        if (isAudioPlayback && mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        } else if (!isAudioPlayback && videoView.isPlaying()) {
            videoView.pause();
        }
    }

    private void stopMedia() {

        if (isAudioPlayback && mediaPlayer != null) {
            try {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            } catch (Exception e) {
                mediaPlayer.reset();
                try {
                    mediaPlayer.setDataSource(this, mediaUri);
                    mediaPlayer.prepareAsync();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } else if (!isAudioPlayback && mediaUri != null) {

            try {
                videoView.pause();
                videoView.seekTo(0);
            } catch (Exception e) {
                videoView.stopPlayback();
                videoView.setVideoURI(mediaUri);
            }
        }
    }

    private void restartMedia() {
        if (isAudioPlayback && mediaPlayer != null && isAudioPrepared) {
            mediaPlayer.seekTo(0);
            mediaPlayer.start();
        } else if (!isAudioPlayback && mediaUri != null && isVideoPrepared) {
            videoView.seekTo(0);
            videoView.start();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE && resultCode == RESULT_OK && data != null) {
            mediaUri = data.getData();
            if (mediaUri != null) {
                String type = getContentResolver().getType(mediaUri);
                boolean isAudio = type != null && type.startsWith("audio/");
                playMediaUri(mediaUri, isAudio);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}