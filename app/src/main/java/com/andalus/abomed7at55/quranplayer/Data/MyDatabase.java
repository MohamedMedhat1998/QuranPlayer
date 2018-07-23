package com.andalus.abomed7at55.quranplayer.Data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {FavoriteSura.class},version = 2)
public abstract class MyDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "APP_DATA_BASE";
    public abstract FavoriteSuraDao favoriteSuraDao();

}
