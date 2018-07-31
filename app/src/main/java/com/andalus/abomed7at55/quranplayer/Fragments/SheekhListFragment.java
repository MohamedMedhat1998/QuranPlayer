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

import com.andalus.abomed7at55.quranplayer.Adapters.SheekhListAdapter;
import com.andalus.abomed7at55.quranplayer.Interfaces.OnSheekhItemClickListener;
import com.andalus.abomed7at55.quranplayer.Networking.UrlBuilder;
import com.andalus.abomed7at55.quranplayer.Objects.Sheekh;
import com.andalus.abomed7at55.quranplayer.Objects.Sura;
import com.andalus.abomed7at55.quranplayer.PlayerActivity;
import com.andalus.abomed7at55.quranplayer.R;
import com.andalus.abomed7at55.quranplayer.SurasListActivity;
import com.andalus.abomed7at55.quranplayer.Utils.JsonParser;
import com.andalus.abomed7at55.quranplayer.Utils.LanguageStorage;
import com.andalus.abomed7at55.quranplayer.Loaders.NetworkingLoader;

import org.json.JSONException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SheekhListFragment extends Fragment implements LoaderManager.LoaderCallbacks<String>,OnSheekhItemClickListener {

    private static final int ID = 5;
    private static final String SCROLL_POSITION_KEY = "scroll_position";
    private static final String SHEEKH_ARRAY_LIST_KEY = "sheekh_list";

    @BindView(R.id.rv_sheekh_list)
    RecyclerView rvSheekhList;
    @BindView(R.id.pb_sheekh_list_indicator)
    ProgressBar pbSheekhListIndicator;
    @BindView(R.id.tv_sheekh_list_no_internet)
    TextView tvSheekhListNoInternet;

    private ArrayList<Sheekh> mSheekhArrayList;
    private SheekhListAdapter mSheekhListAdapter;

    private Context mContext;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sheekh_list,container,false);
        mContext = view.getContext();
        ButterKnife.bind(this,view);
        if(savedInstanceState == null){
            if(isOnline()){
                getLoaderManager().initLoader(ID,null,this);
            }else{
                tvSheekhListNoInternet.setVisibility(View.VISIBLE);
            }
        }else{
            mSheekhArrayList = savedInstanceState.getParcelableArrayList(SHEEKH_ARRAY_LIST_KEY);
            if(mSheekhArrayList!=null){
                rvSheekhList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                mSheekhListAdapter = new SheekhListAdapter(mSheekhArrayList,this);
                rvSheekhList.setAdapter(mSheekhListAdapter);
            }else{
                if(isOnline()){
                    getLoaderManager().initLoader(ID,null,this);
                    pbSheekhListIndicator.setVisibility(View.VISIBLE);
                }else{
                    tvSheekhListNoInternet.setVisibility(View.VISIBLE);
                }
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
        pbSheekhListIndicator.setVisibility(View.VISIBLE);
        return new NetworkingLoader(getContext(), UrlBuilder.buildLanguageUrl(preferences.getString(LanguageStorage.PREFERENCE_LANGUAGE_KEY,null)));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        rvSheekhList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        pbSheekhListIndicator.setVisibility(View.INVISIBLE);
        try {
            mSheekhArrayList = JsonParser.parseSheekhs(data);
            mSheekhListAdapter = new SheekhListAdapter(mSheekhArrayList,this);
            rvSheekhList.setAdapter(mSheekhListAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }


    @Override
    public void onSheekhItemClicked(ArrayList<Integer> ids, String streamingServer,int sheekhId,String sheekhName,String rewaya) {
        Intent i = new Intent(getActivity(), SurasListActivity.class);
        i.putExtra(Sura.IDS_KEY,ids);
        i.putExtra(Sura.STREAMING_SERVER_ROOT_KEY,streamingServer);
        i.putExtra(Sheekh.SHEEKH_ID_KEY,sheekhId);
        i.putExtra(Sheekh.SHEEKH_NAME_KEY,sheekhName);
        i.putExtra(Sheekh.REWAYA_KEY,rewaya);
        startActivity(i);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SHEEKH_ARRAY_LIST_KEY,mSheekhArrayList);
    }

    private boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}
