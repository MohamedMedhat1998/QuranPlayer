package com.andalus.abomed7at55.quranplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import com.andalus.abomed7at55.quranplayer.Fragments.SurasListFragment;
import com.andalus.abomed7at55.quranplayer.Objects.Sheekh;
import com.andalus.abomed7at55.quranplayer.Objects.Sura;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SurasListActivity extends AppCompatActivity {

    private static final String SURA_LIST_FRAGMENT_KEY = "sura_list_key";

    private SurasListFragment mSurasListFragment;

    @BindView(R.id.tv_toolbar_sheekh_name)
    TextView tvToolbarSheekhName;
    @BindView(R.id.tv_toolbar_rewaya)
    TextView tvToolbarRewaya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suras_list);
        ButterKnife.bind(this);

        tvToolbarSheekhName.setText(getIntent().getExtras().getString(Sheekh.SHEEKH_NAME_KEY));
        tvToolbarRewaya.setText(getIntent().getExtras().getString(Sheekh.REWAYA_KEY));

        if(savedInstanceState == null){
            loadSurasListFragment();
        }else{
            mSurasListFragment = (SurasListFragment) getSupportFragmentManager().getFragment(savedInstanceState,SURA_LIST_FRAGMENT_KEY);
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
        getSupportFragmentManager().putFragment(outState,SURA_LIST_FRAGMENT_KEY,mSurasListFragment);
    }


}
