package com.andalus.abomed7at55.quranplayer.Data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class FavoriteSura {

    private static final String SURA_ID = "sura_id";
    private static final String SURA_NAME = "sura_name";

    private static final String SHEEKH_ID = "sheekh_id";
    private static final String SHEEKH_NAME = "sheekh_name";

    private static final String REWAYA = "rewaya";
    private static final String STREAMING_SERVER="streaming_server";

    @NonNull
    @PrimaryKey
    private int uniqueId;

    @ColumnInfo(name = SURA_ID)
    private String mSuraId;

    @ColumnInfo(name = SURA_NAME)
    private String mSuraName;

    @ColumnInfo(name = SHEEKH_ID)
    private String mSheekhId;

    @ColumnInfo(name = SHEEKH_NAME)
    private String mSheekhName;

    @ColumnInfo(name = REWAYA)
    private String mRewaya;

    @ColumnInfo(name = STREAMING_SERVER)
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
}
