package com.andalus.abomed7at55.quranplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.andalus.abomed7at55.quranplayer.Fragments.SurasListFragment;
import com.andalus.abomed7at55.quranplayer.Objects.Sheekh;
import com.andalus.abomed7at55.quranplayer.Objects.Sura;

public class SurasListActivity extends AppCompatActivity {

    private static final String SURA_LIST_FRAGMENT_KEY = "sura_list_key";

    private SurasListFragment mSurasListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suras_list);
        Log.d("SuraListLifeCycle","onCreate");
        if(savedInstanceState == null){
            loadSurasListFragment();
            Log.d("MyActivity","SuraList created");
        }else{
            mSurasListFragment = (SurasListFragment) getSupportFragmentManager().getFragment(savedInstanceState,SURA_LIST_FRAGMENT_KEY);
            Log.d("MyActivity","SuraList from bundle");
        }
    }

    private void loadSurasListFragment(){
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList(Sura.IDS_KEY,getIntent().getExtras().getIntegerArrayList(Sura.IDS_KEY));
        bundle.putString(Sura.STREAMING_SERVER_ROOT_KEY,getIntent().getExtras().getString(Sura.STREAMING_SERVER_ROOT_KEY));
        bundle.putInt(Sheekh.SHEEKH_ID_KEY,getIntent().getExtras().getInt(Sheekh.SHEEKH_ID_KEY));
        bundle.putString(Sheekh.SHEEKH_NAME_KEY,getIntent().getExtras().getString(Sheekh.SHEEKH_NAME_KEY));
        bundle.putString(Sheekh.REWAYA_KEY,getIntent().getExtras().getString(Sheekh.REWAYA_KEY));
        mSurasListFragment = new SurasListFragment();
        mSurasListFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_suars_list_activity,mSurasListFragment).commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("SuraListLifeCycle","onSavedInstanceState");
        getSupportFragmentManager().putFragment(outState,SURA_LIST_FRAGMENT_KEY,mSurasListFragment);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("SuraListLifeCycle","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("SuraListLifeCycle","onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("SuraListLifeCycle","onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("SuraListLifeCycle","onDestroy");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("SuraListLifeCycle","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("SuraListLifeCycle","onResume");
    }
}
