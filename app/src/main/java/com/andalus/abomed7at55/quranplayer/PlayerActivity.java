package com.andalus.abomed7at55.quranplayer;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.andalus.abomed7at55.quranplayer.Data.FavoriteSura;
import com.andalus.abomed7at55.quranplayer.Data.MyDatabase;
import com.andalus.abomed7at55.quranplayer.Interfaces.OnAudioCompletionListener;
import com.andalus.abomed7at55.quranplayer.Objects.Sheekh;
import com.andalus.abomed7at55.quranplayer.Objects.Sura;
import com.andalus.abomed7at55.quranplayer.Utils.PlayerService;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//TODO Optimise and support savedInstantState
public class PlayerActivity extends AppCompatActivity implements OnAudioCompletionListener, SeekBar.OnSeekBarChangeListener, LoaderManager.LoaderCallbacks<Object> {

    private static final int LOADER_ID = 20;

    boolean mBound = true;


    private static final String TAG = "AudioPlayer";

    private PlayerService mPlayerService;

    private final Handler handler = new Handler();
    private Runnable runnable;

    private Sura targetSura;
    private static FavoriteSura favoriteSura;
    private int suraId, sheekhId;
    private String suraName, sheekhName;
    private String streamingServer;
    private String rewaya;


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
    @BindView(R.id.ib_favorite_switch)
    ImageButton ibFavoriteSwitch;
    //TODO handle this switch

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        ButterKnife.bind(this);
        targetSura = getIntent().getExtras().getParcelable(Sura.SURA_OBJECT_KEY);
        mSeekBar.setOnSeekBarChangeListener(this);
        setFavoriteSuraArguments();
    }

    private void setFavoriteSuraArguments(){
        suraId = targetSura.getId();
        suraName = targetSura.getName();
        streamingServer = targetSura.getServer();
        sheekhId = getIntent().getExtras().getInt(Sheekh.SHEEKH_ID_KEY);
        sheekhName = getIntent().getExtras().getString(Sheekh.SHEEKH_NAME_KEY);
        rewaya = getIntent().getExtras().getString(Sheekh.REWAYA_KEY);
        favoriteSura = new FavoriteSura(suraId*1000+sheekhId,suraId+"",suraName,sheekhId+"",sheekhName,rewaya,streamingServer);
    }

    @Override
    protected void onStart() {
        super.onStart();
            Intent i = new Intent(PlayerActivity.this, PlayerService.class);
            i.putExtra(Sura.STREAMING_SERVER_KEY,targetSura.getServer());
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
            mPlayerService.setOnAudioCompletionListener(PlayerActivity.this);
            tvDuration.setText(msToMilitary(mPlayerService.getDuration()));
            tvProgress.setText(msToMilitary(mPlayerService.getProgress()));
            mBound = true;

            runnable = new Runnable() {
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
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBound = false;
        }
    };

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
        handler.removeCallbacks(runnable);
        if(isFinishing()){
            mPlayerService.resetMedia();
            unbindService(mServiceConnection);
            mBound = false;
        }
    }

    @OnClick(R.id.ib_favorite_switch)
    void onFavoriteButtonClicked(){
        Log.d("Click","Favorite Button");
        getSupportLoaderManager().initLoader(LOADER_ID,null,this);
    }

    @OnClick(R.id.btn_play)
    void onPlayClicked(){
        handler.postDelayed(runnable,0);
        if(mBound){
            mPlayerService.playMedia();
        }
    }

    @OnClick(R.id.btn_pause)
    void onPauseClicked(){
        handler.removeCallbacks(runnable);
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
    public void onAudioCompletion() {
        try {
            handler.removeCallbacks(runnable);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        double y = i/100.0;
        if(b){
            mPlayerService.seekTo((int) (y*mPlayerService.getDuration()));
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @NonNull
    @Override
    public Loader<Object> onCreateLoader(int id, @Nullable Bundle args) {
        Log.d("Loader","OnCreateLoader");
        return new CustomLoader(getBaseContext());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Object> loader, Object data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Object> loader) {

    }

    private static class CustomLoader extends AsyncTaskLoader<Object>{
        MyDatabase myDatabase;
        CustomLoader(@NonNull Context context) {
            super(context);
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            forceLoad();
        }

        @Nullable
        @Override
        public Object loadInBackground() {
            Log.d("Loader","LoadInBackGround");
            myDatabase = Room.databaseBuilder(getContext(),MyDatabase.class,MyDatabase.DATABASE_NAME).build();
            myDatabase.favoriteSuraDao().insertAll(favoriteSura);
            return null;
        }
    }
}
