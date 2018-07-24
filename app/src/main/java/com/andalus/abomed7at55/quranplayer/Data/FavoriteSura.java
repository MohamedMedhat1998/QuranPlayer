package com.andalus.abomed7at55.quranplayer.Data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class FavoriteSura {

    public static final String FAVORITE_SURA_ID = "favorite_sura_id";
    public static final String FAVORITE_SURA_NAME = "favorite_sura_name";

    public static final String FAVORITE_SHEEKH_ID = "favorite_sheekh_id";
    public static final String FAVORITE_SHEEKH_NAME = "favorite_sheekh_name";

    public static final String FAVORITE_REWAYA = "favorite_rewaya";
    public static final String FAVORITE_STREAMING_SERVER ="favorite_streaming_server";

    @NonNull
    @PrimaryKey
    private int uniqueId;

    @ColumnInfo(name = FAVORITE_SURA_ID)
    private String mSuraId;

    @ColumnInfo(name = FAVORITE_SURA_NAME)
    private String mSuraName;

    @ColumnInfo(name = FAVORITE_SHEEKH_ID)
    private String mSheekhId;

    @ColumnInfo(name = FAVORITE_SHEEKH_NAME)
    private String mSheekhName;

    @ColumnInfo(name = FAVORITE_REWAYA)
    private String mRewaya;

    @ColumnInfo(name = FAVORITE_STREAMING_SERVER)
    private String mStreamingServer;

    @NonNull
    public int getUniqueId() {
        return uniqueId;
    }

    public FavoriteSura(@NonNull int uniqueId, String mSuraId, String mSuraName, String mSheekhId, String mSheekhName, String mRewaya, String mStreamingServer) {
        this.uniqueId = uniqueId;
        this.mSuraId = mSuraId;
        this.mSuraName = mSuraName;
        this.mSheekhId = mSheekhId;
        this.mSheekhName = mSheekhName;
        this.mRewaya = mRewaya;
        this.mStreamingServer = mStreamingServer;
    }

    public String getSuraId() {
        return mSuraId;
    }

    public String getSuraName() {
        return mSuraName;
    }

    public String getSheekhId() {
        return mSheekhId;
    }

    public String getSheekhName() {
        return mSheekhName;
    }

    public String getRewaya() {
        return mRewaya;
    }

    public String getStreamingServer() {
        return mStreamingServer;
    }

    public String getOfflineName(){
        return mSheekhName+" - "+mSuraName+" - "+mRewaya;
    }
}
