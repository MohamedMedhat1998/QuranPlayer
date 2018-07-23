package com.andalus.abomed7at55.quranplayer.Utils;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.andalus.abomed7at55.quranplayer.Interfaces.OnAudioCompletionListener;
import com.andalus.abomed7at55.quranplayer.Objects.Sura;

import java.io.IOException;

public class PlayerService extends Service implements MediaPlayer.OnCompletionListener {

    private final IBinder mBinder = new LocalBinder();

    private MediaPlayer mMediaPlayer;
    private OnAudioCompletionListener mOnAudioCompletionListener;


    public class LocalBinder extends Binder{

        public PlayerService getPlayerService(){
            return PlayerService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        MyFlags.setIsFirstPlayerRun(true);
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        String mStreamingServer = intent.getExtras().getString(Sura.STREAMING_SERVER_KEY);
        try {
            mMediaPlayer.setDataSource(mStreamingServer);
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mBinder;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        mOnAudioCompletionListener.onAudioCompletion();
        mMediaPlayer.stop();
        try {
            mMediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void resetMedia(){
        mMediaPlayer.stop();
        mMediaPlayer.reset();
    }

    public int getProgress(){
        return mMediaPlayer.getCurrentPosition();
    }

    public int getDuration(){
        return mMediaPlayer.getDuration();
    }

    public void seekTo(int progress){
        mMediaPlayer.seekTo(progress);
    }

    public void setOnAudioCompletionListener(OnAudioCompletionListener onAudioCompletionListener){
        mOnAudioCompletionListener = onAudioCompletionListener;
    }

}
