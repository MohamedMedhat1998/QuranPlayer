package com.andalus.abomed7at55.quranplayer.Objects;

public class Sura {
    private int mId;
    private String mName;
    private String mServer;

    public Sura(int id, String name, String server){
        mId = id;
        mName = name;
        mServer = server;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getServer() {
        return mServer;
    }
}
