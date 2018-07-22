package com.andalus.abomed7at55.quranplayer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.andalus.abomed7at55.quranplayer.Objects.Sura;
import com.andalus.abomed7at55.quranplayer.Utils.PlayerService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//TODO Optimise and support savedInstantState
public class PlayerActivity extends AppCompatActivity {

    private static final int LOADER_ID = 20;

    private static final String TAG = "AudioPlayer";

    private PlayerService mPlayerService;

    @BindView(R.id.btn_play)
    Button btnPlay;
    @BindView(R.id.btn_pause)
    Button btnPause;
    @BindView(R.id.btn_forward)
    Button btnForward;
    @BindView(R.id.btn_back)
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        //Toast.makeText(getBaseContext(),getIntent().getExtras().getString(Sura.STREAMING_SERVER_KEY),Toast.LENGTH_LONG).show();
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent i = new Intent(PlayerActivity.this, PlayerService.class);
        i.putExtra(Sura.STREAMING_SERVER_KEY,getIntent().getExtras().getString(Sura.STREAMING_SERVER_KEY));
        try {
            bindService(i,mServiceConnection, Context.BIND_AUTO_CREATE);
        }catch (Exception e){
            e.printStackTrace();
        }
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
        //unbindService(mServiceConnection);
        /*mMediaController.hide();
        mMediaPlayer.stop();
        mMediaPlayer.release();*/
    }

    @OnClick(R.id.btn_play)
    void onPlayClicked(){
        mPlayerService.playMedia();
    }

    @OnClick(R.id.btn_pause)
    void onPauseClicked(){
        mPlayerService.pauseMedia();
    }

    @OnClick(R.id.btn_back)
    void onBackClicked(){
        mPlayerService.backMedia();
    }

    @OnClick(R.id.btn_forward)
    void onForwardClicked(){
        mPlayerService.forwardMedia();
    }
}
