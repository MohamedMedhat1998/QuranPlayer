package com.andalus.abomed7at55.quranplayer.Networking;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;

import static android.content.Context.DOWNLOAD_SERVICE;

public class Downloader {
    private String mDownloadLink;
    private DownloadManager.Request mRequest;
    private DownloadManager mDownloadManager;
    private String mDownloadPath;
    private String mPackageName;
    private Uri mUri;
    private String mFileName;
    private File mDownloadedFile;
    private Context mContext;

    public Downloader(Context context, String url, String fileName){
        mContext = context;
        mDownloadManager = (DownloadManager)mContext.getSystemService(DOWNLOAD_SERVICE);
        mPackageName = mContext.getPackageName();
        mFileName = fileName;
        mDownloadPath = Environment.getExternalStorageDirectory()+"/Android/data/"+mPackageName+"/files/downloads/";
        mDownloadedFile = new File(mDownloadPath+mFileName);
        mDownloadLink = url;
    }

    public void startDownload(){
        mUri = Uri.parse(mDownloadLink);
        mRequest = new DownloadManager.Request(mUri);
        DownloadManager.Request request = new DownloadManager.Request(mUri);
        request.setDestinationInExternalFilesDir(mContext,"/downloads",mFileName);
        mDownloadManager.enqueue(request);
    }

    public String getDownloadPath(){ return mDownloadPath;}
}

