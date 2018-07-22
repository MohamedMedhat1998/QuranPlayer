package com.andalus.abomed7at55.quranplayer;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.andalus.abomed7at55.quranplayer.Objects.Sura;
import com.andalus.abomed7at55.quranplayer.Utils.MyFlags;
import com.andalus.abomed7at55.quranplayer.Utils.PlayerService;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//TODO Optimise and support savedInstantState
public class PlayerActivity extends AppCompatActivity {

    private static final int LOADER_ID = 20;

    boolean mBound = true;


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
    @BindView(R.id.seek_bar)
    SeekBar mSeekBar;
    @BindView(R.id.tv_progress)
    TextView tvProgress;
    @BindView(R.id.tv_duration)
    TextView tvDuration;

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
            tvDuration.setText(msToMilitary(mPlayerService.getDuration()));
            final Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                int x;
                double y;
                @Override
                public void run() {
                    y = (mPlayerService.getProgress()+0.0)/(mPlayerService.getDuration()+0.0);
                    x = (int) (y*100);
                    mSeekBar.setProgress(x);
                    handler.postDelayed(this,1000);
                    tvProgress.setText(msToMilitary(mPlayerService.getProgress()));
                }
            };
            handler.postDelayed(runnable,0);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBound = false;
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
//        if(isRunning == NOT_RUNNING){
//            Log.d("IsRunning","");
//            unbindService(mServiceConnection);
//            mPlayerService.resetMedia();
//        }
        /*mMediaController.hide();
        mMediaPlayer.stop();
        mMediaPlayer.release();*/
    }

    private String msToMilitary(int millis){
        String stringHours,stringMinutes,stringSeconds;
        long hours,minutes,seconds;

        hours = TimeUnit.MILLISECONDS.toHours(millis);
        minutes = TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis));
        seconds =  TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis));

        if(hours <= 9){
            stringHours = "0" + hours;
        }else{
            stringHours = hours+"";
        }
        if(minutes <= 9){
            stringMinutes = "0" + minutes;
        }else {
            stringMinutes = minutes+"";
        }
        if(seconds <= 9){
            stringSeconds = "0" + seconds;
        }else{
            stringSeconds = seconds+"";
        }
        if(hours==0){
            return stringMinutes+":"+stringSeconds;
        }else{
            return stringHours+":"+stringMinutes+":"+stringSeconds;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("LifeCycle","Activity Destroyed");
        //mPlayerService.resetMedia();
        if(isFinishing()){
            Log.d("IsFinisheing","Yes");
            mPlayerService.resetMedia();
            unbindService(mServiceConnection);
            mBound = false;
        }else {
            Log.d("IsFinisheing","No");
        }
    }

    @OnClick(R.id.btn_play)
    void onPlayClicked(){
        if(mBound){
            mPlayerService.playMedia();
        }
    }

    @OnClick(R.id.btn_pause)
    void onPauseClicked(){
        if(mBound){
            mPlayerService.pauseMedia();
        }
    }

    @OnClick(R.id.btn_back)
    void onBackClicked(){
        if(mBound){
            mPlayerService.backMedia();
        }
    }

    @OnClick(R.id.btn_forward)
    void onForwardClicked(){
        if(mBound){
            mPlayerService.forwardMedia();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
