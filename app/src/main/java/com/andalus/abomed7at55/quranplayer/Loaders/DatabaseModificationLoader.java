package com.andalus.abomed7at55.quranplayer.Loaders;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.andalus.abomed7at55.quranplayer.Data.FavoriteSura;
import com.andalus.abomed7at55.quranplayer.Data.MyDatabase;
import com.google.firebase.analytics.FirebaseAnalytics;

public class DatabaseModificationLoader
        extends AsyncTaskLoader<Boolean> {

    private FavoriteSura mFavoriteSura;
    private FirebaseAnalytics mFirebaseAnalytics;


    public DatabaseModificationLoader(@NonNull Context context,FavoriteSura favoriteSura) {
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
        Log.d("Loader", "LoadInBackGround");
        boolean isFavoriteNow;
        MyDatabase myDatabase = Room.databaseBuilder(getContext(), MyDatabase.class, MyDatabase.DATABASE_NAME).build();
        int id = Integer.parseInt(mFavoriteSura.getSuraId()) * 1000 + Integer.parseInt(mFavoriteSura.getSheekhId());
        if (myDatabase.favoriteSuraDao().getById(id + "") == null) {
            myDatabase.favoriteSuraDao().insertAll(mFavoriteSura);
            Log.d("Database", "Item Was Not Found and inserted");
            isFavoriteNow = true;

            Bundle suraBundle = new Bundle();
            suraBundle.putString(FirebaseAnalytics.Param.ITEM_ID, "sura"+mFavoriteSura.getSuraId());
            suraBundle.putString(FirebaseAnalytics.Param.ITEM_NAME, mFavoriteSura.getSuraName());
            suraBundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Sura Favorite");
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, suraBundle);

            Bundle sheekhBundle = new Bundle();
            sheekhBundle.putString(FirebaseAnalytics.Param.ITEM_ID, "sheekh"+mFavoriteSura.getSheekhId());
            sheekhBundle.putString(FirebaseAnalytics.Param.ITEM_NAME, mFavoriteSura.getSheekhName());
            sheekhBundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Sheekh Favorite");
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, sheekhBundle);

        } else {
            myDatabase.favoriteSuraDao().deleteById(id);
            Log.d("Database", "Item Was Found and removed");
            isFavoriteNow = false;
        }
        return isFavoriteNow;
    }
}
