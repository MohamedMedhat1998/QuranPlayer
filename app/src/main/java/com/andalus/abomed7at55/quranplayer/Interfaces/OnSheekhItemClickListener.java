package com.andalus.abomed7at55.quranplayer.Interfaces;

import android.widget.TextView;

import java.util.ArrayList;

public interface OnSheekhItemClickListener {
    void onSheekhItemClicked(ArrayList<Integer> ids, String streamingServer, int sheekhId, String sheekhName, String rewaya, TextView tvSheekhName,TextView tvRewaya);
}
