package com.andalus.abomed7at55.quranplayer.Data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity
public class FavoriteSura implements Parcelable{

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

    protected FavoriteSura(Parcel in) {
        uniqueId = in.readInt();
        mSuraId = in.readString();
        mSuraName = in.readString();
        mSheekhId = in.readString();
        mSheekhName = in.readString();
        mRewaya = in.readString();
        mStreamingServer = in.readString();
    }

    public static final Creator<FavoriteSura> CREATOR = new Creator<FavoriteSura>() {
        @Override
        public FavoriteSura createFromParcel(Parcel in) {
            return new FavoriteSura(in);
        }

        @Override
        public FavoriteSura[] newArray(int size) {
            return new FavoriteSura[size];
        }
    };

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
        parcel.writeString(mStreamingServer);
    }
}
