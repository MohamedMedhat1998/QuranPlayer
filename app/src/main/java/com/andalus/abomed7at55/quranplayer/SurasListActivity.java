package com.andalus.abomed7at55.quranplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.andalus.abomed7at55.quranplayer.Fragments.SurasListFragment;
import com.andalus.abomed7at55.quranplayer.Objects.Sheekh;
import com.andalus.abomed7at55.quranplayer.Objects.Sura;

public class SurasListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suras_list);
        if(savedInstanceState == null){
            loadSurasListFragment();
        }
    }

    private void loadSurasListFragment(){
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList(Sura.IDS_KEY,getIntent().getExtras().getIntegerArrayList(Sura.IDS_KEY));
        bundle.putString(Sura.STREAMING_SERVER_ROOT_KEY,getIntent().getExtras().getString(Sura.STREAMING_SERVER_ROOT_KEY));
        bundle.putInt(Sheekh.SHEEKH_ID_KEY,getIntent().getExtras().getInt(Sheekh.SHEEKH_ID_KEY));
        bundle.putString(Sheekh.SHEEKH_NAME_KEY,getIntent().getExtras().getString(Sheekh.SHEEKH_NAME_KEY));
        bundle.putString(Sheekh.REWAYA_KEY,getIntent().getExtras().getString(Sheekh.REWAYA_KEY));
        SurasListFragment surasListFragment = new SurasListFragment();
        surasListFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_suars_list_activity,surasListFragment).commit();
    }
}
