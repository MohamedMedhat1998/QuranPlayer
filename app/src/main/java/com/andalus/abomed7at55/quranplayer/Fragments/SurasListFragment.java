package com.andalus.abomed7at55.quranplayer.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.andalus.abomed7at55.quranplayer.Adapters.SurasListAdapter;
import com.andalus.abomed7at55.quranplayer.Interfaces.OnSuraClickListener;
import com.andalus.abomed7at55.quranplayer.Networking.UrlBuilder;
import com.andalus.abomed7at55.quranplayer.Objects.Sheekh;
import com.andalus.abomed7at55.quranplayer.Objects.Sura;
import com.andalus.abomed7at55.quranplayer.PlayerActivity;
import com.andalus.abomed7at55.quranplayer.R;
import com.andalus.abomed7at55.quranplayer.Utils.JsonParser;
import com.andalus.abomed7at55.quranplayer.Utils.LanguageStorage;
import com.andalus.abomed7at55.quranplayer.Loaders.NetworkingLoader;

import org.json.JSONException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SurasListFragment extends Fragment implements LoaderManager.LoaderCallbacks<String>,OnSuraClickListener {

    private static final int ID = 10;
    private static final String SURA_ARRAY_LIST_KEY = "sura_key";

    private Context mContext;

    @BindView(R.id.rv_suras_list)
    RecyclerView rvSurasList;
    @BindView(R.id.pb_sura_list_indicator)
    ProgressBar pbSuraListIndicator;
    @BindView(R.id.tv_sura_list_no_internet)
    TextView tvSuraListNoInternet;

    private ArrayList<Integer> mSurasIds;
    private String mStreamingServerRoot;

    private ArrayList<Sura> mSuraArrayList;
    private SurasListAdapter mSurasListAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suras_list,container,false);
        mContext = view.getContext();
        ButterKnife.bind(this,view);
        if(savedInstanceState==null){
            if(isOnline()){
                getLoaderManager().initLoader(ID,null,this);
            }else{
                tvSuraListNoInternet.setVisibility(View.VISIBLE);
            }
        }else {
            mSuraArrayList = savedInstanceState.getParcelableArrayList(SURA_ARRAY_LIST_KEY);
            if(mSuraArrayList!=null){
                rvSurasList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                mSurasListAdapter = new SurasListAdapter(mSuraArrayList,this);
                rvSurasList.setAdapter(mSurasListAdapter);
            }else{
                pbSuraListIndicator.setVisibility(View.VISIBLE);
                getLoaderManager().initLoader(ID,null,this);
            }
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        pbSuraListIndicator.setVisibility(View.VISIBLE);
        return new NetworkingLoader(getContext(), UrlBuilder.buildSurasNamesUrl(preferences.getString(LanguageStorage.PREFERENCE_LANGUAGE_KEY,null)));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {
            pbSuraListIndicator.setVisibility(View.INVISIBLE);
            mSurasIds = getArguments().getIntegerArrayList(Sura.IDS_KEY);
            mStreamingServerRoot = getArguments().getString(Sura.STREAMING_SERVER_ROOT_KEY);
            mSuraArrayList = JsonParser.parseSura(data,mSurasIds,mStreamingServerRoot);
            rvSurasList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
            mSurasListAdapter = new SurasListAdapter(mSuraArrayList,this);
            rvSurasList.setAdapter(mSurasListAdapter);
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
        i.putExtra(PlayerActivity.TAG,PlayerActivity.TAG_FROM_SURA_LIST);
        startActivity(i);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SURA_ARRAY_LIST_KEY,mSuraArrayList);
    }

    private boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}
