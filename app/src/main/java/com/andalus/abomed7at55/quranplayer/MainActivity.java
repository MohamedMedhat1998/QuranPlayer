package com.andalus.abomed7at55.quranplayer;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.andalus.abomed7at55.quranplayer.Fragments.LanguageFragment;
import com.andalus.abomed7at55.quranplayer.Fragments.SheekhListFragment;
import com.andalus.abomed7at55.quranplayer.Widget.PlayerWidget;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.andalus.abomed7at55.quranplayer.Utils.LanguageStorage.PREFERENCE_LANGUAGE_KEY;
import static com.andalus.abomed7at55.quranplayer.Utils.LanguageStorage.getLanguage;

public class MainActivity extends AppCompatActivity {
    //final values
    private static final String IS_FIRST_RUN_KEY = "isFirst";
    private static final int FIRST_RUN = 0;
    private static final int NOT_FIRST_RUN = 10;
    private static final String LANGUAGE_FRAGMENT_KEY = "lang_frag";
    private static final String SHEEKH_LIST_FRAGMENT_KEY = "sheekh_frag";

    //Binding Views
    @BindView(R.id.btn_next_language)
    Button btnNextLanguage;
    @BindView(R.id.adView)
    AdView mAdView;

    private LanguageFragment mLanguageFragment;
    private SheekhListFragment mSheekhListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        MobileAds.initialize(this, getString(R.string.adKey));

        updateWidget();

        if(isFirstRun()){
            if(savedInstanceState == null){
                loadLanguageFragment();
            }else{
                mLanguageFragment = (LanguageFragment) getSupportFragmentManager().getFragment(savedInstanceState,LANGUAGE_FRAGMENT_KEY);
            }
        }else{
            if(savedInstanceState == null){
                loadSheekhListFragment();
            }else {
                mSheekhListFragment = (SheekhListFragment) getSupportFragmentManager().getFragment(savedInstanceState,SHEEKH_LIST_FRAGMENT_KEY);
            }
            hideNextButton();
            showBannerAd();
        }

    }

    /**
     * A method to detect whether this is the first run for the user or not
     * @return true if it's the first run, false if it's not the first run
     */
    private boolean isFirstRun(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int isFirstRun = sharedPreferences.getInt(IS_FIRST_RUN_KEY,FIRST_RUN);
        return isFirstRun == FIRST_RUN;
    }

    private void updateWidget(){
        AppWidgetManager myAppWidgetManager = AppWidgetManager.getInstance(this);
        int[] ids = myAppWidgetManager.getAppWidgetIds(new ComponentName(this, PlayerWidget.class));
        PlayerWidget.customUpdate(getBaseContext(), myAppWidgetManager, ids);
    }

    /**
     * This method sets the run state to be not the first run
     */
    private void modifyRunCount(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(IS_FIRST_RUN_KEY,NOT_FIRST_RUN);
        editor.apply();
    }

    /**
     * Loads the language fragment in the fragment container in the MainActivity
     */
    private void loadLanguageFragment(){
        mLanguageFragment = new LanguageFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_main_activity,mLanguageFragment).commit();
    }

    /**
     * Loads the sheekh list fragment in the fragment container in the MainActivity
     */
    private void loadSheekhListFragment() {
        mSheekhListFragment = new SheekhListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_main_activity,mSheekhListFragment).commit();
    }

    /**
     * This method is used to remove fragments from their containers
     */
    private void clearFragmentContainer() {
        for (Fragment fragment:getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }

    /**
     * This method updates a shared preferences value that is responsible for controlling the language of the app
     */
    private void runPreferenceLanguageProcess() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        RadioGroup radioGroup = findViewById(R.id.rg_language_selection);
        int checkedId = radioGroup.getCheckedRadioButtonId();
        String selectedLanguage = getLanguage(checkedId);
        editor.putString(PREFERENCE_LANGUAGE_KEY,selectedLanguage);
        editor.apply();
    }

    @OnClick(R.id.btn_next_language)
    void onNextButtonClicked(){
        runPreferenceLanguageProcess();
        //TODO uncomment this before submitting
        //modifyRunCount();
        clearFragmentContainer();
        loadSheekhListFragment();
        hideNextButton();
        showBannerAd();
    }

    private void hideNextButton(){
        btnNextLanguage.setVisibility(View.GONE);
    }

    private void showBannerAd(){
        mAdView.setVisibility(View.VISIBLE);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_favorite:
                startActivity(new Intent(MainActivity.this,FavoriteActivity.class));
                break;
            case R.id.item_offline:
                startActivity(new Intent(MainActivity.this,OfflineActivity.class));
                break;
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        int fragmentsSize = fragments.size();

        for(int i = 0; i < fragmentsSize ; i++){
            if(fragments.get(i) instanceof LanguageFragment){
                getSupportFragmentManager().putFragment(outState,LANGUAGE_FRAGMENT_KEY,mLanguageFragment);
                break;
            }
        }

        for(int i = 0; i < fragmentsSize ; i++){
            if(fragments.get(i) instanceof SheekhListFragment){
                getSupportFragmentManager().putFragment(outState,SHEEKH_LIST_FRAGMENT_KEY,mSheekhListFragment);
                break;
            }
        }
    }

}
