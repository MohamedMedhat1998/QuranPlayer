package com.andalus.abomed7at55.quranplayer;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioGroup;

import com.andalus.abomed7at55.quranplayer.Fragments.LanguageFragment;
import com.andalus.abomed7at55.quranplayer.Fragments.SheekhListFragment;
import com.andalus.abomed7at55.quranplayer.Networking.Networking;
import com.andalus.abomed7at55.quranplayer.Objects.Sheekh;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.andalus.abomed7at55.quranplayer.Utils.LanguageStorage.PREFERENCE_LANGUAGE_KEY;
import static com.andalus.abomed7at55.quranplayer.Utils.LanguageStorage.getLanguage;

public class MainActivity extends AppCompatActivity {
    private static final String DOWNLOAD_LINK ="http://server6.mp3quran.net/thubti/001.mp3";
    //final values
    private static final String IS_FIRST_RUN_KEY = "isFirst";
    private static final int FIRST_RUN = 0;
    private static final int NOT_FIRST_RUN = 10;

    //Binding Views
    @BindView(R.id.btn_next_language)
    Button btnNextLanguage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Downloader downloader = new Downloader(MainActivity.this,DOWNLOAD_LINK,"001");
        downloader.startDownload();*/
        ButterKnife.bind(this);
        Sheekh sheekh = new Sheekh();
        int numbers[] = sheekh.convertSurasString("1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114");
        for(int i =0; i <numbers.length ; i++){
            Log.d("number",numbers[i] + "");
        }
        if(isFirstRun()){
            if(savedInstanceState == null){
                loadLanguageFragment();
            }
        }else{
            if(savedInstanceState == null){
                loadSheekhListFragment();
            }
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
        LanguageFragment languageFragment = new LanguageFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_main_activity,languageFragment).commit();
    }

    /**
     * Loads the sheekh list fragment in the fragment container in the MainActivity
     */
    private void loadSheekhListFragment() {
        SheekhListFragment sheekhListFragment = new SheekhListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_main_activity,sheekhListFragment).commit();
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
        //hideNextButton();
    }

}
