package com.andalus.abomed7at55.quranplayer.Data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {FavoriteSura.class,OfflineSura.class},version = 2)
public abstract class MyDatabase extends RoomDatabase {

    private static MyDatabase mMyDatabase;
    //With the help of : https://android.jlelse.eu/android-architecture-components-room-livedata-and-viewmodel-fca5da39e26b
    public static MyDatabase getDatabaseStaticInstance(Context context){
        if(mMyDatabase == null){
            mMyDatabase = Room.databaseBuilder(context,MyDatabase.class,MyDatabase.DATABASE_NAME).build();
        }
        return mMyDatabase;
    }

    public static final String DATABASE_NAME = "APP_DATA_BASE";
    public abstract FavoriteSuraDao favoriteSuraDao();
    public abstract OfflineSuraDao offlineSuraDao();

}
