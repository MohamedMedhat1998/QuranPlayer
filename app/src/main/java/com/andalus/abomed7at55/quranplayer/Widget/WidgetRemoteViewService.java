package com.andalus.abomed7at55.quranplayer.Widget;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

public class WidgetRemoteViewService extends RemoteViewsService{
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d("WidgetRemoteViewService","onGetViewFactory");
        return new WidgetRemoteViewsFactory(getBaseContext());
    }
}
