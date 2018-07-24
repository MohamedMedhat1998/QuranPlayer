package com.andalus.abomed7at55.quranplayer.Fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andalus.abomed7at55.quranplayer.Adapters.SurasListAdapter;
import com.andalus.abomed7at55.quranplayer.Interfaces.OnSuraClickListener;
import com.andalus.abomed7at55.quranplayer.Networking.UrlBuilder;
import com.andalus.abomed7at55.quranplayer.Objects.Sheekh;
import com.andalus.abomed7at55.quranplayer.Objects.Sura;
import com.andalus.abomed7at55.quranplayer.PlayerActivity;
import com.andalus.abomed7at55.quranplayer.R;
import com.andalus.abomed7at55.quranplayer.Utils.JsonParser;
import com.andalus.abomed7at55.quranplayer.Utils.LanguageStorage;
import com.andalus.abomed7at55.quranplayer.Utils.MyLoader;

import org.json.JSONException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SurasListFragment extends Fragment implements LoaderManager.LoaderCallbacks<String>,OnSuraClickListener {

    private static final int ID = 10;

    @BindView(R.id.rv_suras_list)
    RecyclerView rvSurasList;

    private ArrayList<Integer> mSurasIds;
    private String mStreamingServerRoot;

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
        try {
            mSurasIds = getArguments().getIntegerArrayList(Sura.IDS_KEY);
            mStreamingServerRoot = getArguments().getString(Sura.STREAMING_SERVER_ROOT_KEY);
            ArrayList<Sura> suraArrayList = JsonParser.parseSura(data,mSurasIds,mStreamingServerRoot);
            rvSurasList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
            rvSurasList.setAdapter(new SurasListAdapter(suraArrayList,this));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    @Override
    public void onSuraClick(Sura sura) {
        Intent i = new Intent(getActivity(), PlayerActivity.class);
        i.putExtra(Sura.SURA_OBJECT_KEY,sura);
        i.putExtra(Sheekh.SHEEKH_ID_KEY,getArguments().getInt(Sheekh.SHEEKH_ID_KEY));
        i.putExtra(Sheekh.SHEEKH_NAME_KEY,getArguments().getString(Sheekh.SHEEKH_NAME_KEY));
        i.putExtra(Sheekh.REWAYA_KEY,getArguments().getString(Sheekh.REWAYA_KEY));
        startActivity(i);
    }
}
