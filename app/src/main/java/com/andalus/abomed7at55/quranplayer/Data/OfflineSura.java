package com.andalus.abomed7at55.quranplayer.Data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity
public class OfflineSura implements Parcelable{
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

    protected OfflineSura(Parcel in) {
        uniqueId = in.readInt();
        mSuraId = in.readString();
        mSuraName = in.readString();
        mSheekhId = in.readString();
        mSheekhName = in.readString();
        mRewaya = in.readString();
        mStreamingPath = in.readString();
        mOfflineFileName = in.readString();
    }

    public static final Creator<OfflineSura> CREATOR = new Creator<OfflineSura>() {
        @Override
        public OfflineSura createFromParcel(Parcel in) {
            return new OfflineSura(in);
        }

        @Override
        public OfflineSura[] newArray(int size) {
            return new OfflineSura[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(uniqueId);
        parcel.writeString(mSuraId);
        parcel.writeString(mSuraName);
        parcel.writeString(mSheekhId);
        parcel.writeString(mSheekhName);
        parcel.writeString(mRewaya);
        parcel.writeString(mStreamingPath);
        parcel.writeString(mOfflineFileName);
    }
}
