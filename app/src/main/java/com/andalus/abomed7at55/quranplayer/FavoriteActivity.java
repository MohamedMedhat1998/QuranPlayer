package com.andalus.abomed7at55.quranplayer;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<FavoriteSura>> {

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
        return new CustomLoader(getBaseContext());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<FavoriteSura>> loader, ArrayList<FavoriteSura> data) {
        rvFavoriteList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rvFavoriteList.setAdapter(new FavoriteListAdapter(data));
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<FavoriteSura>> loader) {

    }

    private static class CustomLoader extends AsyncTaskLoader<ArrayList<FavoriteSura>> {

        CustomLoader(Context context) {
            super(context);
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            forceLoad();
        }

        @Override
        public ArrayList<FavoriteSura> loadInBackground() {
            MyDatabase database = Room.databaseBuilder(getContext(), MyDatabase.class,MyDatabase.DATABASE_NAME).build();
            return (ArrayList<FavoriteSura>) database.favoriteSuraDao().getAll();
        }
    }
}
