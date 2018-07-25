package com.andalus.abomed7at55.quranplayer.Loaders;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.andalus.abomed7at55.quranplayer.Data.FavoriteSura;
import com.andalus.abomed7at55.quranplayer.Data.MyDatabase;
import com.andalus.abomed7at55.quranplayer.Data.OfflineSura;
import com.andalus.abomed7at55.quranplayer.Networking.Downloader;

public class DownloaderLoader extends AsyncTaskLoader<Boolean> {

    private FavoriteSura mFavoriteSura;

    public DownloaderLoader(@NonNull Context context,FavoriteSura favoriteSura) {
        super(context);
        mFavoriteSura = favoriteSura;
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
        //TODO check the unique Id before downloading
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
            //TODO Download and insert into database
        }else{
            isOk = false;
            //TODO Already Downloaded
        }
        return isOk;
    }
}
