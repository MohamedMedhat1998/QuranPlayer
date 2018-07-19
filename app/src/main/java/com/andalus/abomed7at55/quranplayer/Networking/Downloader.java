package com.andalus.abomed7at55.quranplayer.Networking;

import android.app.Activity;
import android.app.DownloadManager;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

import static android.content.Context.DOWNLOAD_SERVICE;

public class Downloader {
    private String mDownloadLink;
    private DownloadManager.Request mRequest;
    private DownloadManager mDownloadManager;
    private String mDownloadPath;
    private String mPackageName;
    private Activity mCurrentActivity;
    private Uri mUri;
    private String mFileName;
    private File mDownloadedFile;

    public Downloader(Activity currentActivity,String url,String fileName){
        mCurrentActivity = currentActivity;
        mDownloadManager = (DownloadManager)mCurrentActivity.getSystemService(DOWNLOAD_SERVICE);
        mPackageName = mCurrentActivity.getApplication().getPackageName();
        mFileName = fileName;
        mDownloadPath = Environment.getExternalStorageDirectory()+"/Android/data/"+mPackageName+"/files/downloads/";
        mDownloadedFile = new File(mDownloadPath+mFileName);
        mDownloadLink = url;
    }

    public void startDownload(){
        mUri = Uri.parse(mDownloadLink);
        mRequest = new DownloadManager.Request(mUri);
        DownloadManager.Request request = new DownloadManager.Request(mUri);
        request.setDestinationInExternalFilesDir(mCurrentActivity.getBaseContext(),"/downloads",mFileName);
        mDownloadManager.enqueue(request);

    }
    public void setDownloadLink(String url){
        mDownloadLink = url;
    }
    //-------------------------getters------------
    public String getDownloadLink(){
        return mDownloadLink;
    }
    public File getDownloadedFile(){ return mDownloadedFile;}
    public String getDownloadPath(){ return mDownloadPath;}
}

