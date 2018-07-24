package com.andalus.abomed7at55.quranplayer.Data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class OfflineSura {
    public static final String OFFLINE_SURA_ID = "offline_sura_id";
    public static final String OFFLINE_SURA_NAME = "offline_sura_name";

    public static final String OFFLINE_SHEEKH_ID = "offline_sheekh_id";
    public static final String OFFLINE_SHEEKH_NAME = "offline_sheekh_name";

    public static final String OFFLINE_REWAYA = "offline_rewaya";
    public static final String OFFLINE_STREAMING_PATH ="offline_streaming_path";

    public static final String OFFLINE_FILE_NAME = "offline_file_name";

    @NonNull
    @PrimaryKey
    private int uniqueId;

    @ColumnInfo(name = OFFLINE_SURA_ID)
    private String mSuraId;

    @ColumnInfo(name = OFFLINE_SURA_NAME)
    private String mSuraName;

    @ColumnInfo(name = OFFLINE_SHEEKH_ID)
    private String mSheekhId;

    @ColumnInfo(name = OFFLINE_SHEEKH_NAME)
    private String mSheekhName;

    @ColumnInfo(name = OFFLINE_REWAYA)
    private String mRewaya;

    @ColumnInfo(name = OFFLINE_STREAMING_PATH)
    private String mStreamingPath;

    @ColumnInfo(name = OFFLINE_FILE_NAME)
    private String mOfflineFileName;

    @NonNull
    public int getUniqueId() {
        return uniqueId;
    }

    public OfflineSura(@NonNull int uniqueId, String mSuraId, String mSuraName, String mSheekhId, String mSheekhName, String mRewaya, String mStreamingPath,String offlineFileName) {
        this.uniqueId = uniqueId;
        this.mSuraId = mSuraId;
        this.mSuraName = mSuraName;
        this.mSheekhId = mSheekhId;
        this.mSheekhName = mSheekhName;
        this.mRewaya = mRewaya;
        this.mStreamingPath = mStreamingPath;
        this.mOfflineFileName = offlineFileName;
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

    public String getStreamingPath() {
        return mStreamingPath;
    }

    public String getOfflineFileName(){
        return mOfflineFileName;
    }
}
