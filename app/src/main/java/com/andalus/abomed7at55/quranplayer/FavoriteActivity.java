package com.andalus.abomed7at55.quranplayer;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.andalus.abomed7at55.quranplayer.Adapters.FavoriteListAdapter;
import com.andalus.abomed7at55.quranplayer.Data.FavoriteSura;
import com.andalus.abomed7at55.quranplayer.Data.MyDatabase;
import com.andalus.abomed7at55.quranplayer.Interfaces.OnFavoriteSuraClickListener;
import com.andalus.abomed7at55.quranplayer.Loaders.DatabaseQueryingLoader;
import com.andalus.abomed7at55.quranplayer.Objects.Sheekh;
import com.andalus.abomed7at55.quranplayer.Objects.Sura;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<FavoriteSura>>,OnFavoriteSuraClickListener {

    private static final int LOADER_ID = 40;

    @BindView(R.id.rv_favorite_list)
    RecyclerView rvFavoriteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ButterKnife.bind(this);
        getSupportLoaderManager().initLoader(LOADER_ID,null,this);
    }

    @NonNull
    @Override
    public Loader<ArrayList<FavoriteSura>> onCreateLoader(int id, @Nullable Bundle args) {
        return new DatabaseQueryingLoader(getBaseContext());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<FavoriteSura>> loader, ArrayList<FavoriteSura> data) {
        rvFavoriteList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rvFavoriteList.setAdapter(new FavoriteListAdapter(data,this));
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<FavoriteSura>> loader) {

    }


    @Override
    public void onFavoriteSuraClick(int suraId, String suraName, int sheekhId, String sheekhName, String streamingServer, String rewaya) {
        Intent i = new Intent(FavoriteActivity.this, PlayerActivity.class);
        i.putExtra(FavoriteSura.FAVORITE_SURA_ID,suraId);
        i.putExtra(FavoriteSura.FAVORITE_SURA_NAME,suraName);
        i.putExtra(FavoriteSura.FAVORITE_SHEEKH_ID,sheekhId);
        i.putExtra(FavoriteSura.FAVORITE_SHEEKH_NAME,sheekhName);
        i.putExtra(FavoriteSura.FAVORITE_STREAMING_SERVER,streamingServer);
        i.putExtra(FavoriteSura.FAVORITE_REWAYA,rewaya);
        startActivity(i);
    }
}
