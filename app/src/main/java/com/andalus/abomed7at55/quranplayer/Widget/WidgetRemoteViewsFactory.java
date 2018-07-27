package com.andalus.abomed7at55.quranplayer.Widget;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.andalus.abomed7at55.quranplayer.Data.MyDatabase;
import com.andalus.abomed7at55.quranplayer.Data.OfflineSura;
import com.andalus.abomed7at55.quranplayer.R;

import java.util.ArrayList;

public class WidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private ArrayList<OfflineSura> mData;
    private Context mContext;

    public WidgetRemoteViewsFactory(Context context){
        mContext = context;
        mData = new ArrayList<>();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        Log.d("RVFS","onDataSetChanged");
        MyDatabase database = Room.databaseBuilder(mContext,MyDatabase.class,MyDatabase.DATABASE_NAME).build();
        mData = (ArrayList<OfflineSura>) database.offlineSuraDao().getAll();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        Log.d("DataSize",mData.size()+"");
        return mData.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        OfflineSura tempSura = mData.get(i);
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.player_widget);
        views.setTextViewText(R.id.tv_widget_sheekh_name,tempSura.getSheekhName());
        views.setTextViewText(R.id.tv_widget_sura_name,tempSura.getSuraName());
        views.setTextViewText(R.id.tv_widget_rewaya,tempSura.getRewaya());
        Log.d("getViewAt",i+" - "+tempSura.getSuraName());
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
