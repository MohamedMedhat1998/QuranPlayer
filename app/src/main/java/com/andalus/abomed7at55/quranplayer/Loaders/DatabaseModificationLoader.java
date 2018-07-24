package com.andalus.abomed7at55.quranplayer.Loaders;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.andalus.abomed7at55.quranplayer.Data.FavoriteSura;
import com.andalus.abomed7at55.quranplayer.Data.MyDatabase;

public class DatabaseModificationLoader
        extends AsyncTaskLoader<Boolean> {

    private FavoriteSura mFavoriteSura;

    public DatabaseModificationLoader(@NonNull Context context,FavoriteSura favoriteSura) {
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
        Log.d("Loader", "LoadInBackGround");
        boolean isFavoriteNow;
        MyDatabase myDatabase = Room.databaseBuilder(getContext(), MyDatabase.class, MyDatabase.DATABASE_NAME).build();
        int id = Integer.parseInt(mFavoriteSura.getSuraId()) * 1000 + Integer.parseInt(mFavoriteSura.getSheekhId());
        if (myDatabase.favoriteSuraDao().getById(id + "") == null) {
            myDatabase.favoriteSuraDao().insertAll(mFavoriteSura);
            Log.d("Database", "Item Was Not Found and inserted");
            isFavoriteNow = true;
        } else {
            myDatabase.favoriteSuraDao().deleteById(id);
            Log.d("Database", "Item Was Found and removed");
            isFavoriteNow = false;
        }
        return isFavoriteNow;
    }
}
