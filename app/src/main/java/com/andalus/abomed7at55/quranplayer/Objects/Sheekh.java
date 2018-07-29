package com.andalus.abomed7at55.quranplayer.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;

public class Sheekh implements Parcelable{
    private String mName;
    private String mServer;
    private String mRewaya;
    private int mCount;
    private ArrayList<Integer> mSurasIds;
    private long mId;

    public static final String SHEEKH_ID_KEY = "sheekh_id";
    public static final String SHEEKH_NAME_KEY = "sheekh_name";
    public static final String REWAYA_KEY = "sheekh_rewaya";

    public Sheekh(long id,String name, String server, String rewaya, int count, String suras) {
        mId = id;
        mName = name;
        mServer = server;
        mRewaya = rewaya;
        mCount = count;
        mSurasIds = new ArrayList<>(Arrays.asList(convertSurasString(suras)));
    }

    protected Sheekh(Parcel in) {
        mName = in.readString();
        mServer = in.readString();
        mRewaya = in.readString();
        mCount = in.readInt();
        mId = in.readLong();
    }

    public static final Creator<Sheekh> CREATOR = new Creator<Sheekh>() {
        @Override
        public Sheekh createFromParcel(Parcel in) {
            return new Sheekh(in);
        }

        @Override
        public Sheekh[] newArray(int size) {
            return new Sheekh[size];
        }
    };

    private Integer[] convertSurasString(String suras){
        String surasArray[] = suras.split(",");
        int x = surasArray.length;
        Integer[] arr = new Integer[x];
        for(int i = 0; i < x ; i++){
            arr[i] = Integer.parseInt(surasArray[i]);
        }
        return arr;
    }

    public long getId(){
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getServer() {
        return mServer;
    }

    public String getRewaya() {
        return mRewaya;
    }

    public int getCount() {
        return mCount;
    }

    public ArrayList<Integer> getSurasIds() {
        return mSurasIds;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeString(mServer);
        parcel.writeString(mRewaya);
        parcel.writeInt(mCount);
        parcel.writeLong(mId);
    }
}
