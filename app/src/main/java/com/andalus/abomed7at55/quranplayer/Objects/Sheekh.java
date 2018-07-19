package com.andalus.abomed7at55.quranplayer.Objects;

public class Sheekh {
    private String mName;
    private String mServer;
    private String mRewaya;
    private int mCount;
    private String mSuras;
    private int mSurasIds[];

    public Sheekh(String name, String server, String rewaya, int count, String suras) {
        mName = name;
        mServer = server;
        mRewaya = rewaya;
        mCount = count;
        mSuras = suras;
        mSurasIds = convertSurasString(mSuras);
    }

    private int[] convertSurasString(String suras){
        String surasArray[] = suras.split(",");
        int x = surasArray.length;
        int[] arr = new int[x];
        for(int i = 0; i < x ; i++){
            arr[i] = Integer.parseInt(surasArray[i]);
        }
        return arr;
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

    public int[] getSurasIds() {
        return mSurasIds;
    }
}
