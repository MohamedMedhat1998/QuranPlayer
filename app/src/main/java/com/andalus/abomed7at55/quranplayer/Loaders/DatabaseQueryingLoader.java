package com.andalus.abomed7at55.quranplayer.Loaders;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.AsyncTaskLoader;

import com.andalus.abomed7at55.quranplayer.Data.FavoriteSura;
import com.andalus.abomed7at55.quranplayer.Data.MyDatabase;

import java.util.ArrayList;

public class DatabaseQueryingLoader
        extends AsyncTaskLoader<ArrayList<FavoriteSura>> {

    public DatabaseQueryingLoader(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public ArrayList<FavoriteSura> loadInBackground() {
        MyDatabase database = Room.databaseBuilder(getContext(), MyDatabase.class, MyDatabase.DATABASE_NAME).build();
        return (ArrayList<FavoriteSura>) database.favoriteSuraDao().getAll();
    }

}
