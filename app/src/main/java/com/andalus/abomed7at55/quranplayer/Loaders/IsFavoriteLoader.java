package com.andalus.abomed7at55.quranplayer.Loaders;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.andalus.abomed7at55.quranplayer.Data.FavoriteSura;
import com.andalus.abomed7at55.quranplayer.Data.MyDatabase;

public class IsFavoriteLoader extends AsyncTaskLoader<Boolean> {

    private FavoriteSura mFavoriteSura;

    public IsFavoriteLoader(Context context,FavoriteSura favoriteSura) {
        super(context);
        mFavoriteSura = favoriteSura;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public Boolean loadInBackground() {
        boolean isFavoriteNow;
        MyDatabase myDatabase = Room.databaseBuilder(getContext(), MyDatabase.class, MyDatabase.DATABASE_NAME).build();
        int id = Integer.parseInt(mFavoriteSura.getSuraId()) * 1000 + Integer.parseInt(mFavoriteSura.getSheekhId());
        if (myDatabase.favoriteSuraDao().getById(id + "") == null) {
            isFavoriteNow = false;
            Log.d("Favorite","Not Favorite");
        } else {
            Log.d("Favorite","Favorite");
            isFavoriteNow = true;
        }
        return isFavoriteNow;
    }
}
