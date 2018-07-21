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

import com.andalus.abomed7at55.quranplayer.Adapters.SheekhListAdapter;
import com.andalus.abomed7at55.quranplayer.Interfaces.OnSheekhItemClickListener;
import com.andalus.abomed7at55.quranplayer.Networking.UrlBuilder;
import com.andalus.abomed7at55.quranplayer.Objects.Sheekh;
import com.andalus.abomed7at55.quranplayer.Objects.Sura;
import com.andalus.abomed7at55.quranplayer.R;
import com.andalus.abomed7at55.quranplayer.SurasListActivity;
import com.andalus.abomed7at55.quranplayer.Utils.JsonParser;
import com.andalus.abomed7at55.quranplayer.Utils.LanguageStorage;
import com.andalus.abomed7at55.quranplayer.Utils.MyLoader;

import org.json.JSONException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SheekhListFragment extends Fragment implements LoaderManager.LoaderCallbacks<String>,OnSheekhItemClickListener {

    private static final int ID = 5;

    @BindView(R.id.rv_sheekh_list)
    RecyclerView rvSheekhList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sheekh_list,container,false);
        ButterKnife.bind(this,view);
        getLoaderManager().initLoader(ID,null,this);
        return view;
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        return new MyLoader(getContext(), UrlBuilder.buildLanguageUrl(preferences.getString(LanguageStorage.PREFERENCE_LANGUAGE_KEY,null)));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        rvSheekhList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        try {
            ArrayList<Sheekh> sheekhArrayList = JsonParser.parseSheekhs(data);
            rvSheekhList.setAdapter(new SheekhListAdapter(sheekhArrayList,this));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }


    @Override
    public void onSheekhItemClicked(ArrayList<Integer> ids, String streamingServer) {
        Intent i = new Intent(getActivity(), SurasListActivity.class);
        i.putExtra(Sura.IDS_KEY,ids);
        i.putExtra(Sura.STREAMING_SERVER_ROOT_KEY,streamingServer);
        startActivity(i);
    }
}
