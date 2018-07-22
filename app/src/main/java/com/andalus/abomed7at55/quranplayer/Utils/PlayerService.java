package com.andalus.abomed7at55.quranplayer.Utils;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.andalus.abomed7at55.quranplayer.Objects.Sura;

import java.io.IOException;

public class PlayerService extends Service {

    private final IBinder mBinder = new LocalBinder();

    private static String mStreamingServer;
    private MediaPlayer mMediaPlayer;


    public class LocalBinder extends Binder{

        public PlayerService getPlayerService(){
            return PlayerService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mStreamingServer = intent.getExtras().getString(Sura.STREAMING_SERVER_KEY);
        try {
            mMediaPlayer.setDataSource(mStreamingServer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.stop();
                mediaPlayer.reset();
            }
        });

        try {
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mBinder;
    }

    public void playMedia(){
        mMediaPlayer.start();
    }

    public void pauseMedia(){
        mMediaPlayer.pause();
    }

    public void forwardMedia(){
        mMediaPlayer.seekTo(mMediaPlayer.getCurrentPosition()+2000);
    }

    public void backMedia(){
        mMediaPlayer.seekTo(mMediaPlayer.getCurrentPosition()-2000);
    }

}