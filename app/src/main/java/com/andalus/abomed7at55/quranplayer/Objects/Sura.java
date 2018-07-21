package com.andalus.abomed7at55.quranplayer.Objects;

public class Sura {
    private int mId;
    private String mName;
    private String mServer;

    public static final String IDS_KEY = "ids";
    public static final String STREAMING_SERVER_ROOT_KEY = "root_server";
    public static final String STREAMING_SERVER_KEY = "streaming_server";


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
