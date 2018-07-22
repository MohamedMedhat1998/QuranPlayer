package com.andalus.abomed7at55.quranplayer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.MediaController;
import android.widget.Toast;

import com.andalus.abomed7at55.quranplayer.Objects.Sura;
import com.andalus.abomed7at55.quranplayer.Utils.PlayerService;

import java.io.IOException;
//TODO Optimise and support savedInstantState
public class PlayerActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener,
        MediaController.MediaPlayerControl{

    private static final int LOADER_ID = 20;

    private static final String TAG = "AudioPlayer";

    private MediaPlayer mMediaPlayer;
    private MediaController mMediaController;
    private PlayerService mPlayerService;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        //Toast.makeText(getBaseContext(),getIntent().getExtras().getString(Sura.STREAMING_SERVER_KEY),Toast.LENGTH_LONG).show();
        startStreaming();
    }

    private void startStreaming(){
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaController = new MediaController(this);
        new Player().execute(getIntent().getExtras().getString(Sura.STREAMING_SERVER_KEY));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent i = new Intent(PlayerActivity.this, PlayerService.class);
        bindService(i,mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            PlayerService.LocalBinder binder = (PlayerService.LocalBinder) iBinder;
            mPlayerService = binder.getPlayerService();

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(mServiceConnection);
        /*mMediaController.hide();
        mMediaPlayer.stop();
        mMediaPlayer.release();*/
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mMediaController.show();
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onPrepared");
        mMediaController.setMediaPlayer(this);
        mMediaController.setAnchorView(findViewById(R.id.tv_anchor_view));

        handler.post(new Runnable() {
            public void run() {
                mMediaController.setEnabled(true);
                mMediaController.show();
            }
        });
    }

    @Override
    public void start() {
        mMediaPlayer.start();
        mPlayerService.playMedia();
    }

    @Override
    public void pause() {
        mMediaPlayer.pause();
    }

    @Override
    public int getDuration() {
        return mMediaPlayer.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return mMediaPlayer.getCurrentPosition();
    }

    @Override
    public void seekTo(int i) {
        mMediaPlayer.seekTo(i);
    }

    @Override
    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }


    class Player extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            Boolean prepared;

            try {
                mMediaPlayer.setDataSource(strings[0]);
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });

                mMediaPlayer.prepare();
                prepared = true;

            } catch (Exception e) {
                Log.e("MyAudioStreamingApp", e.getMessage());
                prepared = false;
            }

            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            mMediaPlayer.start();
        }
    }
}
