package com.andalus.abomed7at55.quranplayer;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.andalus.abomed7at55.quranplayer.Adapters.OfflineListAdapter;
import com.andalus.abomed7at55.quranplayer.Data.OfflineSura;
import com.andalus.abomed7at55.quranplayer.Loaders.DatabaseQueryingLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OfflineActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<OfflineSura>> {

    private static final int LOADER_ID = 55;

    @BindView(R.id.rv_offline_sura_list)
    RecyclerView rvOfflineSuraList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
        ButterKnife.bind(this);
        getSupportLoaderManager().initLoader(LOADER_ID,null,this).forceLoad();
    }

    @NonNull
    @Override
    public Loader<ArrayList<OfflineSura>> onCreateLoader(int id, @Nullable Bundle args) {
        return new DatabaseQueryingLoader<OfflineSura>(getBaseContext(),DatabaseQueryingLoader.TAG_QUERY_OFFLINE);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<OfflineSura>> loader, ArrayList<OfflineSura> data) {
        rvOfflineSuraList.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false));
        rvOfflineSuraList.setAdapter(new OfflineListAdapter(data));
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<OfflineSura>> loader) {

    }
}
