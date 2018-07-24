package com.andalus.abomed7at55.quranplayer.Objects;

import android.os.Parcel;
import android.os.Parcelable;

public class Sura implements Parcelable {
    private int mId;
    private String mName;
    private String mServer;

    public static final String IDS_KEY = "ids";
    public static final String STREAMING_SERVER_ROOT_KEY = "root_server";
    public static final String STREAMING_SERVER_KEY = "streaming_server";
    public static final String SURA_OBJECT_KEY = "sura";


    public Sura(int id, String name, String server){
        mId = id;
        mName = name;
        mServer = server;
    }

    protected Sura(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mServer = in.readString();
    }

    public static final Creator<Sura> CREATOR = new Creator<Sura>() {
        @Override
        public Sura createFromParcel(Parcel in) {
            return new Sura(in);
        }

        @Override
        public Sura[] newArray(int size) {
            return new Sura[size];
        }
    };

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getServer() {
        return mServer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mName);
        parcel.writeString(mServer);
    }
}
