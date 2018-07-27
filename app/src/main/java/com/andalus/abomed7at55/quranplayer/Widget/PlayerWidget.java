package com.andalus.abomed7at55.quranplayer.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.andalus.abomed7at55.quranplayer.PlayerActivity;
import com.andalus.abomed7at55.quranplayer.R;

/**
 * Implementation of App Widget functionality.
 */
public class PlayerWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Log.d("PlayerWidget","updateAppWidget");
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_grid_view);
        //new WidgetBackgroundThread(context,views,appWidgetManager,appWidgetId).execute();

        Intent i = new Intent(context,WidgetRemoteViewService.class);
        views.setRemoteAdapter(R.id.widget_player_grid_view,i);

        Intent playerActivityIntent = new Intent(context, PlayerActivity.class);
        PendingIntent playerActivityPendingIntent = PendingIntent.getActivity(context,5,playerActivityIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_player_grid_view,playerActivityPendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.widget_player_grid_view);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.player_widget);
            //new WidgetBackgroundThread(context,views,appWidgetManager,appWidgetId).execute();
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.widget_player_grid_view);
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public static void customUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.widget_player_grid_view);
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }


}

