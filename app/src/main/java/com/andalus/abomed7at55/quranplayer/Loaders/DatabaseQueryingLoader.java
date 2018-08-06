package com.andalus.abomed7at55.quranplayer.Loaders;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.AsyncTaskLoader;

import com.andalus.abomed7at55.quranplayer.Data.FavoriteSura;
import com.andalus.abomed7at55.quranplayer.Data.MyDatabase;

import java.util.ArrayList;

public class DatabaseQueryingLoader<T>
        extends AsyncTaskLoader<ArrayList<T>> {

    public static final String TAG_QUERY_FAVORITE = "query_favorite";
    public static final String TAG_QUERY_OFFLINE = "query_offline";

    private String mQueryTag;
    private MyDatabase database;

    public DatabaseQueryingLoader(@NonNull Context context,String queryTag) {
        super(context);
        mQueryTag = queryTag;
        database = Room.databaseBuilder(getContext(), MyDatabase.class, MyDatabase.DATABASE_NAME).build();
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public ArrayList<T> loadInBackground() {
        ArrayList<T> dataArrayList = null;
        if(mQueryTag.equals(TAG_QUERY_OFFLINE)){
            dataArrayList = setUpOfflineArrayList();
        }
        return dataArrayList;
    }

    private ArrayList<T> setUpOfflineArrayList(){
        return (ArrayList<T>) database.offlineSuraDao().getAll();
    }

}
