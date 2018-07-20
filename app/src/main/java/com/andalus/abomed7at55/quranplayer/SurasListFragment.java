package com.andalus.abomed7at55.quranplayer;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andalus.abomed7at55.quranplayer.Networking.UrlBuilder;
import com.andalus.abomed7at55.quranplayer.Objects.Sura;
import com.andalus.abomed7at55.quranplayer.Utils.JsonParser;
import com.andalus.abomed7at55.quranplayer.Utils.LanguageStorage;
import com.andalus.abomed7at55.quranplayer.Utils.MyLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SurasListFragment extends Fragment implements LoaderManager.LoaderCallbacks<String> {

    private static final int ID = 10;

    @BindView(R.id.rv_suras_list)
    RecyclerView rvSurasList;
    //TODO Complete this
    /*
    private ArrayList<Integer> mSurasIds;
    private String mStreamingServerRoot;


    public SurasListFragment(ArrayList<Integer> surasIds,String streamingServerRoot){

    }*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suras_list,container,false);
        ButterKnife.bind(this,view);
        getLoaderManager().initLoader(ID,null,this);
        return view;
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        return new MyLoader(getContext(), UrlBuilder.buildSurasNamesUrl(preferences.getString(LanguageStorage.PREFERENCE_LANGUAGE_KEY,null)));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        //TODO Complete this
        //ArrayList<Sura> suraArrayList = JsonParser.parseSura(data,,);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
