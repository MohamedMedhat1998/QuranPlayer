package com.andalus.abomed7at55.quranplayer;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.andalus.abomed7at55.quranplayer.Data.FavoriteSura;
import com.andalus.abomed7at55.quranplayer.Data.OfflineSura;
import com.andalus.abomed7at55.quranplayer.Interfaces.OnAudioCompletionListener;
import com.andalus.abomed7at55.quranplayer.Interfaces.OnPreparationFinishedListener;
import com.andalus.abomed7at55.quranplayer.Loaders.DatabaseModificationLoader;
import com.andalus.abomed7at55.quranplayer.Loaders.DownloaderLoader;
import com.andalus.abomed7at55.quranplayer.Loaders.IsFavoriteLoader;
import com.andalus.abomed7at55.quranplayer.Objects.Sheekh;
import com.andalus.abomed7at55.quranplayer.Objects.Sura;
import com.andalus.abomed7at55.quranplayer.Utils.PlayerService;
import com.andalus.abomed7at55.quranplayer.Widget.PlayerWidget;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayerActivity extends AppCompatActivity implements OnAudioCompletionListener, SeekBar.OnSeekBarChangeListener, LoaderManager.LoaderCallbacks<Boolean> {

    private static final int DATABASE_MODIFICATION_LOADER_ID = 20;
    private static final int IS_FAVORITE_LOADER_ID = 30;
    private static final int DOWNLOAD_LOADER_ID = 10;

    private int canIPrepare = YOU_CAN_NOT_PREPARE;
    private static final int YOU_CAN_NOT_PREPARE = 5;
    private static final int YOU_CAN_PREPARE = 1;
    private static final String PREPARATION_KEY = "prep_key";


    public static final String TAG = "player_tag";
    public static final String TAG_FROM_SURA_LIST = "from_sura_list";
    public static final String TAG_FROM_FAVORITE_LIST = "from_favorite_list";
    public static final String TAG_FROM_OFFLINE_LIST = "from_offline_list";


    boolean mBound = true;

    private PlayerService mPlayerService;

    private final Handler handler = new Handler();
    private Runnable runnable;

    private FavoriteSura favoriteSura;
    private Sura targetSura;
    private int suraId, sheekhId;
    private String suraName, sheekhName;
    private String streamingServer;
    private String rewaya;

    @BindView(R.id.btn_play)
    ImageButton btnPlay;
    @BindView(R.id.btn_forward)
    ImageButton btnForward;
    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.seek_bar)
    SeekBar mSeekBar;
    @BindView(R.id.tv_progress)
    TextView tvProgress;
    @BindView(R.id.tv_duration)
    TextView tvDuration;
    @BindView(R.id.ib_favorite_switch)
    ImageButton ibFavoriteSwitch;
    @BindView(R.id.ib_download)
    ImageButton ibDownload;
    @BindView(R.id.tv_player_sura_name)
    TextView tvPlayerSuraName;
    @BindView(R.id.tv_player_sheekh_name)
    TextView tvPlayerSheekhName;
    @BindView(R.id.tv_player_rewaya)
    TextView tvPlayerRewaya;
    @BindView(R.id.pb_player_indicator)
    ProgressBar pbPlayerIndicator;
    @BindView(R.id.tv_player_no_internet)
    TextView tvPlayerNoInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_player);
        ButterKnife.bind(this);
        disableButtons();
        if(savedInstanceState != null){
            canIPrepare = savedInstanceState.getInt(PREPARATION_KEY);
        }

        targetSura = getIntent().getExtras().getParcelable(Sura.SURA_OBJECT_KEY);
        mSeekBar.setOnSeekBarChangeListener(this);
        setFavoriteSuraArguments();
        getSupportLoaderManager().initLoader(IS_FAVORITE_LOADER_ID, null, this).forceLoad();
    }

    private void setFavoriteSuraArguments() {
        String tag = getIntent().getExtras().getString(TAG);
        switch (tag) {
            case TAG_FROM_SURA_LIST:
                suraId = targetSura.getId();
                suraName = targetSura.getName();
                streamingServer = targetSura.getServer();
                sheekhId = getIntent().getExtras().getInt(Sheekh.SHEEKH_ID_KEY);
                sheekhName = getIntent().getExtras().getString(Sheekh.SHEEKH_NAME_KEY);
                rewaya = getIntent().getExtras().getString(Sheekh.REWAYA_KEY);
                break;
            case TAG_FROM_FAVORITE_LIST:
                Bundle favoriteBundle = getIntent().getExtras();
                suraId = favoriteBundle.getInt(FavoriteSura.FAVORITE_SURA_ID);
                suraName = favoriteBundle.getString(FavoriteSura.FAVORITE_SURA_NAME);
                streamingServer = favoriteBundle.getString(FavoriteSura.FAVORITE_STREAMING_SERVER);
                sheekhId = favoriteBundle.getInt(FavoriteSura.FAVORITE_SHEEKH_ID);
                sheekhName = favoriteBundle.getString(FavoriteSura.FAVORITE_SHEEKH_NAME);
                rewaya = favoriteBundle.getString(FavoriteSura.FAVORITE_REWAYA);
                break;
            case TAG_FROM_OFFLINE_LIST:
                Bundle offlineBundle = getIntent().getExtras();
                suraId = offlineBundle.getInt(OfflineSura.OFFLINE_SURA_ID);
                suraName = offlineBundle.getString(OfflineSura.OFFLINE_SURA_NAME);
                String streamingPath = offlineBundle.getString(OfflineSura.OFFLINE_STREAMING_PATH);
                String fileName = offlineBundle.getString(OfflineSura.OFFLINE_FILE_NAME);
                streamingServer = streamingPath + fileName + ".mp3";
                sheekhId = offlineBundle.getInt(OfflineSura.OFFLINE_SHEEKH_ID);
                sheekhName = offlineBundle.getString(OfflineSura.OFFLINE_SHEEKH_NAME);
                rewaya = offlineBundle.getString(OfflineSura.OFFLINE_REWAYA);
                break;
        }
        favoriteSura = new FavoriteSura(suraId * 1000 + sheekhId, suraId + "", suraName, sheekhId + "", sheekhName, rewaya, streamingServer);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent i = new Intent(PlayerActivity.this, PlayerService.class);
        i.putExtra(Sura.STREAMING_SERVER_KEY, streamingServer);
        try {
            if(!isOnline() && !getIntent().getExtras().getString(TAG).equals(TAG_FROM_OFFLINE_LIST)){
                tvPlayerNoInternet.setVisibility(View.VISIBLE);
            }else{
                bindService(i, mServiceConnection, Context.BIND_AUTO_CREATE);
                if(mPlayerService == null){
                    pbPlayerIndicator.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            PlayerService.LocalBinder binder = (PlayerService.LocalBinder) iBinder;
            mPlayerService = binder.getPlayerService();

            mPlayerService.setOnPreparationFinishedListener(new OnPreparationFinishedListener() {
                @Override
                public void onPreparationFinished() {
                    prepareUI();
                    canIPrepare = YOU_CAN_PREPARE;
                }
            });
            if(canIPrepare == YOU_CAN_PREPARE){
                prepareUI();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBound = false;
        }
    };

    private void prepareUI(){
        pbPlayerIndicator.setVisibility(View.INVISIBLE);
        enableButtons();
        mPlayerService.setOnAudioCompletionListener(PlayerActivity.this);
        tvDuration.setText(msToMilitary(mPlayerService.getDuration()));
        tvProgress.setText(msToMilitary(mPlayerService.getProgress()));

        tvPlayerSuraName.setText(suraName);
        tvPlayerSheekhName.setText(sheekhName);
        tvPlayerRewaya.setText(rewaya);

        tvPlayerSuraName.setContentDescription(suraName);
        tvPlayerSheekhName.setContentDescription(sheekhName);
        tvPlayerRewaya.setContentDescription(rewaya);

        mBound = true;
        switchPlayPauseIcon();
        runnable = new Runnable() {
            int x;
            double y;

            @Override
            public void run() {
                y = (mPlayerService.getProgress() + 0.0) / (mPlayerService.getDuration() + 0.0);
                x = (int) (y * 100);
                mSeekBar.setProgress(x);
                handler.postDelayed(this, 1000);
                tvProgress.setText(msToMilitary(mPlayerService.getProgress()));
            }

        };
        handler.postDelayed(runnable, 0);
    }

    private void enableButtons(){
        ibDownload.setEnabled(true);
        ibFavoriteSwitch.setEnabled(true);
        btnPlay.setEnabled(true);
        btnBack.setEnabled(true);
        btnForward.setEnabled(true);
    }

    private void disableButtons(){
        ibDownload.setEnabled(false);
        ibFavoriteSwitch.setEnabled(false);
        btnPlay.setEnabled(false);
        btnBack.setEnabled(false);
        btnForward.setEnabled(false);
    }

    private String msToMilitary(int millis) {
        String stringHours, stringMinutes, stringSeconds;
        long hours, minutes, seconds;

        hours = TimeUnit.MILLISECONDS.toHours(millis);
        minutes = TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis));
        seconds = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis));

        if (hours <= 9) {
            stringHours = "0" + hours;
        } else {
            stringHours = hours + "";
        }
        if (minutes <= 9) {
            stringMinutes = "0" + minutes;
        } else {
            stringMinutes = minutes + "";
        }
        if (seconds <= 9) {
            stringSeconds = "0" + seconds;
        } else {
            stringSeconds = seconds + "";
        }
        if (hours == 0) {
            return stringMinutes + ":" + stringSeconds;
        } else {
            return stringHours + ":" + stringMinutes + ":" + stringSeconds;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
        if (isFinishing()) {
            try {
                mPlayerService.resetMedia();
                unbindService(mServiceConnection);
                mBound = false;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @OnClick(R.id.ib_download)
    void onDownloadButtonClicked() {
        getSupportLoaderManager().initLoader(DOWNLOAD_LOADER_ID, null, this).forceLoad();
    }

    @OnClick(R.id.ib_favorite_switch)
    void onFavoriteButtonClicked() {
        getSupportLoaderManager().initLoader(DATABASE_MODIFICATION_LOADER_ID, null, this).forceLoad();
    }

    @OnClick(R.id.btn_play)
    void onPlayClicked() {
        handler.postDelayed(runnable, 0);
        if (mBound) {
            if(!mPlayerService.isPlaying()){
                mPlayerService.playMedia();
            }else{
                mPlayerService.pauseMedia();
            }
            switchPlayPauseIcon();
        }

    }

    private void switchPlayPauseIcon(){
        if(mPlayerService.isPlaying()){
            //<div>Icons made by <a href="https://www.flaticon.com/authors/egor-rumyantsev" title="Egor Rumyantsev">Egor Rumyantsev</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>
            btnPlay.setImageResource(R.drawable.pause);
        }else{
            //<div>Icons made by <a href="https://www.flaticon.com/authors/smashicons" title="Smashicons">Smashicons</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>
            btnPlay.setImageResource(R.drawable.play_button);
        }
    }

    @OnClick(R.id.btn_back)
    void onBackClicked() {
        if (mBound) {
            mPlayerService.backMedia();
        }
    }

    @OnClick(R.id.btn_forward)
    void onForwardClicked() {
        if (mBound) {
            mPlayerService.forwardMedia();
        }
    }

    @Override
    public void onAudioCompletion() {
        try {
            switchPlayPauseIcon();
            handler.removeCallbacks(runnable);
            tvProgress.setText(tvDuration.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        double y = i / 100.0;
        if (b) {
            mPlayerService.seekTo((int) (y * mPlayerService.getDuration()));
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    void turnOnStar() {
        //<div>Icons made by <a href="https://www.flaticon.com/authors/smashicons" title="Smashicons">Smashicons</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>
        ibFavoriteSwitch.setImageResource(R.drawable.star_fill);
    }

    void turnOffStar() {
        //<div>Icons made by <a href="https://www.flaticon.com/authors/smashicons" title="Smashicons">Smashicons</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>
        ibFavoriteSwitch.setImageResource(R.drawable.star_empty);
    }

    @NonNull
    @Override
    public Loader<Boolean> onCreateLoader(int id, @Nullable Bundle args) {
        if (id == DATABASE_MODIFICATION_LOADER_ID) {
            return new DatabaseModificationLoader(getBaseContext(), favoriteSura);
        } else if (id == IS_FAVORITE_LOADER_ID) {
            return new IsFavoriteLoader(getBaseContext(), favoriteSura);
        } else if (id == DOWNLOAD_LOADER_ID) {
            return new DownloaderLoader(this, favoriteSura);
        } else {
            return null;
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Boolean> loader, Boolean data) {
        if (loader instanceof DatabaseModificationLoader || loader instanceof IsFavoriteLoader) {
            if (data) {
                turnOnStar();
            } else {
                turnOffStar();
            }
        } else if (loader instanceof DownloaderLoader) {
            if (!data) {
                Toast.makeText(getBaseContext(), R.string.download_failed, Toast.LENGTH_LONG).show();
            }
            AppWidgetManager myAppWidgetManager = AppWidgetManager.getInstance(this);
            int[] ids = myAppWidgetManager.getAppWidgetIds(new ComponentName(this, PlayerWidget.class));
            PlayerWidget.customUpdate(getBaseContext(), myAppWidgetManager, ids);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Boolean> loader) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PREPARATION_KEY,canIPrepare);
    }

    private boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
