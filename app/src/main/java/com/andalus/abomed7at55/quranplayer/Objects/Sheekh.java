package com.andalus.abomed7at55.quranplayer.Objects;

import java.util.ArrayList;
import java.util.Arrays;

public class Sheekh {
    private String mName;
    private String mServer;
    private String mRewaya;
    private int mCount;
    private ArrayList<Integer> mSurasIds;
    private long mId;

    public Sheekh(long id,String name, String server, String rewaya, int count, String suras) {
        mId = id;
        mName = name;
        mServer = server;
        mRewaya = rewaya;
        mCount = count;
        mSurasIds = new ArrayList<>(Arrays.asList(convertSurasString(suras)));
    }

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
}
