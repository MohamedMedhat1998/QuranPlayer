package com.andalus.abomed7at55.quranplayer.Utils;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class PlayerService extends Service {

    private final IBinder mBinder = new LocalBinder();
    private static String mStreamingServer;

    public class LocalBinder extends Binder{

        public PlayerService getPlayerService(){
            return PlayerService.this;
        }

        public void setStreamingServer(String streamingServer){
            mStreamingServer = streamingServer;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void playMedia(){
        Toast.makeText(getApplicationContext(),"Played",Toast.LENGTH_SHORT).show();
    }

    public void pauseMedia(){

    }

    public void forwardMedia(){

    }

    public void backMedia(){

    }

}
