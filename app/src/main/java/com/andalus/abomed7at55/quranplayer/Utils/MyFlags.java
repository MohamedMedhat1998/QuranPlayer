package com.andalus.abomed7at55.quranplayer.Utils;

public class MyFlags {
    private static boolean mIsFirstPlayerRun = false;

    public static boolean isIsFirstPlayerRun() {
        return mIsFirstPlayerRun;
    }

    public static void setIsFirstPlayerRun(boolean mIsFirstPlayerRun) {
        MyFlags.mIsFirstPlayerRun = mIsFirstPlayerRun;
    }
}
