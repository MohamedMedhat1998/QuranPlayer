package com.andalus.abomed7at55.quranplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;

public class OfflineActivity extends AppCompatActivity {

    @BindView(R.id.rv_offline_sura_list)
    RecyclerView rvOfflineSuraList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
    }
}
