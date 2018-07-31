package com.andalus.abomed7at55.quranplayer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.andalus.abomed7at55.quranplayer.Adapters.OfflineListAdapter;
import com.andalus.abomed7at55.quranplayer.Data.OfflineSura;
import com.andalus.abomed7at55.quranplayer.Interfaces.OnOfflineSuraClickListener;
import com.andalus.abomed7at55.quranplayer.Loaders.DatabaseQueryingLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OfflineActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<OfflineSura>>,OnOfflineSuraClickListener {

    private static final int LOADER_ID = 55;
    private static final String OFFLINE_SURA_ARRAY_LIST_KEY = "off_su_al_key";

    private ArrayList<OfflineSura> mData;

    @BindView(R.id.rv_offline_sura_list)
    RecyclerView rvOfflineSuraList;
    @BindView(R.id.tv_no_items_offline_activity)
    TextView tvNoItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
        ButterKnife.bind(this);
        if(savedInstanceState==null){
            getSupportLoaderManager().initLoader(LOADER_ID,null,this).forceLoad();
        }else{
            mData = savedInstanceState.getParcelableArrayList(OFFLINE_SURA_ARRAY_LIST_KEY);
            rvOfflineSuraList.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false));
            rvOfflineSuraList.setAdapter(new OfflineListAdapter(mData,this));
        }
    }

    @NonNull
    @Override
    public Loader<ArrayList<OfflineSura>> onCreateLoader(int id, @Nullable Bundle args) {
        return new DatabaseQueryingLoader<OfflineSura>(getBaseContext(),DatabaseQueryingLoader.TAG_QUERY_OFFLINE);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<OfflineSura>> loader, ArrayList<OfflineSura> data) {
        mData = data;
        rvOfflineSuraList.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false));
        rvOfflineSuraList.setAdapter(new OfflineListAdapter(mData,this));
        if(mData.isEmpty()){
            tvNoItems.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<OfflineSura>> loader) {

    }

    @Override
    public void onOfflineSuraClicked(int suraId, String suraName, int sheekhId, String sheekhName, String streamingPath, String rewaya, String fileName) {
        Intent i = new Intent(OfflineActivity.this,PlayerActivity.class);
        i.putExtra(PlayerActivity.TAG,PlayerActivity.TAG_FROM_OFFLINE_LIST);
        i.putExtra(OfflineSura.OFFLINE_SURA_ID,suraId);
        i.putExtra(OfflineSura.OFFLINE_SURA_NAME,suraName);
        i.putExtra(OfflineSura.OFFLINE_SHEEKH_ID,sheekhId);
        i.putExtra(OfflineSura.OFFLINE_SHEEKH_NAME,sheekhName);
        i.putExtra(OfflineSura.OFFLINE_STREAMING_PATH,streamingPath);
        i.putExtra(OfflineSura.OFFLINE_REWAYA,rewaya);
        i.putExtra(OfflineSura.OFFLINE_FILE_NAME,suraId*1000+sheekhId+"");
        startActivity(i);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(OFFLINE_SURA_ARRAY_LIST_KEY,mData);
    }
}
