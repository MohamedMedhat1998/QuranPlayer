package com.andalus.abomed7at55.quranplayer.Data;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;
//With the help of : https://android.jlelse.eu/android-architecture-components-room-livedata-and-viewmodel-fca5da39e26b
public class FavoriteSuraViewModel extends AndroidViewModel {

    private final LiveData<List<FavoriteSura>> mData;
    private MyDatabase mMyDatabase;

    public FavoriteSuraViewModel(@NonNull Application application) {
        super(application);
        mMyDatabase = MyDatabase.getDatabaseStaticInstance(this.getApplication());
        mData = mMyDatabase.favoriteSuraDao().getAll();
    }

    public LiveData<List<FavoriteSura>> getFavoriteSuraData() {
        return mData;
    }

}
