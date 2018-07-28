package com.andalus.abomed7at55.quranplayer.Loaders;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.andalus.abomed7at55.quranplayer.Data.FavoriteSura;
import com.andalus.abomed7at55.quranplayer.Data.MyDatabase;
import com.andalus.abomed7at55.quranplayer.Data.OfflineSura;
import com.andalus.abomed7at55.quranplayer.Networking.Downloader;
import com.google.firebase.analytics.FirebaseAnalytics;

public class DownloaderLoader extends AsyncTaskLoader<Boolean> {

    private FavoriteSura mFavoriteSura;

    private FirebaseAnalytics mFirebaseAnalytics;

    public DownloaderLoader(@NonNull Context context,FavoriteSura favoriteSura) {
        super(context);
        mFavoriteSura = favoriteSura;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public Boolean loadInBackground() {
        boolean isOk;
        MyDatabase myDatabase = Room.databaseBuilder(getContext(),MyDatabase.class,MyDatabase.DATABASE_NAME).build();
        OfflineSura offlineSura = myDatabase.offlineSuraDao().getById(mFavoriteSura.getUniqueId() +"");
        if(offlineSura == null){
            Downloader downloader = new Downloader(getContext(),mFavoriteSura.getStreamingServer(),mFavoriteSura.getUniqueId()+".mp3");
            downloader.startDownload();
            offlineSura = new OfflineSura(mFavoriteSura.getUniqueId(),
                    mFavoriteSura.getSuraId(),
                    mFavoriteSura.getSuraName(),
                    mFavoriteSura.getSheekhId(),
                    mFavoriteSura.getSheekhName(),
                    mFavoriteSura.getRewaya(),
                    downloader.getDownloadPath(),
                    mFavoriteSura.getOfflineName()+".mp3");
            myDatabase.offlineSuraDao().insertAll(offlineSura);
            isOk = true;

            Bundle suraBundle = new Bundle();
            suraBundle.putString(FirebaseAnalytics.Param.ITEM_ID, "download_sura"+offlineSura.getSuraId());
            suraBundle.putString(FirebaseAnalytics.Param.ITEM_NAME, offlineSura.getSuraName());
            suraBundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Download Sura");
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, suraBundle);

            Bundle sheekhBundle = new Bundle();
            sheekhBundle.putString(FirebaseAnalytics.Param.ITEM_ID, "download_sheekh"+offlineSura.getSheekhId());
            sheekhBundle.putString(FirebaseAnalytics.Param.ITEM_NAME, offlineSura.getSheekhName());
            sheekhBundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Download Sheekh");
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, sheekhBundle);

        }else{
            isOk = false;
        }
        return isOk;
    }
}
