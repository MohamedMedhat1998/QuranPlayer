package com.andalus.abomed7at55.quranplayer;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.andalus.abomed7at55.quranplayer.Adapters.FavoriteListAdapter;
import com.andalus.abomed7at55.quranplayer.Data.FavoriteSura;
import com.andalus.abomed7at55.quranplayer.Data.FavoriteSuraViewModel;
import com.andalus.abomed7at55.quranplayer.Data.MyDatabase;
import com.andalus.abomed7at55.quranplayer.Interfaces.OnFavoriteSuraClickListener;
import com.andalus.abomed7at55.quranplayer.Loaders.DatabaseQueryingLoader;
import com.andalus.abomed7at55.quranplayer.Objects.Sheekh;
import com.andalus.abomed7at55.quranplayer.Objects.Sura;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteActivity extends AppCompatActivity implements OnFavoriteSuraClickListener {

    @BindView(R.id.rv_favorite_list)
    RecyclerView rvFavoriteList;
    @BindView(R.id.tv_no_items_favorite_activity)
    TextView tvNoItemsFavoriteActivity;

    private FavoriteListAdapter mAdapter;

    private FavoriteSuraViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.favorite));

        ButterKnife.bind(this);

        mAdapter = new FavoriteListAdapter(new ArrayList<FavoriteSura>(),this);
        rvFavoriteList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rvFavoriteList.setAdapter(mAdapter);

        viewModel = ViewModelProviders.of(this).get(FavoriteSuraViewModel.class);

        viewModel.getFavoriteSuraData().observe(this, new Observer<List<FavoriteSura>>() {
            @Override
            public void onChanged(@Nullable List<FavoriteSura> favoriteSuras) {
                mAdapter.updateData(favoriteSuras);
                if(mAdapter.getItemCount() == 0){
                    tvNoItemsFavoriteActivity.setVisibility(View.VISIBLE);
                }else{
                    tvNoItemsFavoriteActivity.setVisibility(View.INVISIBLE);
                }
            }
        });
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
        i.putExtra(PlayerActivity.TAG,PlayerActivity.TAG_FROM_FAVORITE_LIST);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == ActionBar.DISPLAY_HOME_AS_UP){
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
