package com.andalus.abomed7at55.quranplayer;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.andalus.abomed7at55.quranplayer.Fragments.LanguageFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.andalus.abomed7at55.quranplayer.Utils.LanguageStorage.PREFERENCE_LANGUAGE_KEY;
import static com.andalus.abomed7at55.quranplayer.Utils.LanguageStorage.getLanguage;

public class SettingsActivity extends AppCompatActivity {

    LanguageFragment mLanguageFragment;
    private static final String LANGUAGE_FRAGMENT_KEY = "language_fragment_key";

    @BindView(R.id.btn_done_language)
    Button btnDoneLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ButterKnife.bind(this);
        if(savedInstanceState == null){
            mLanguageFragment = new LanguageFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.settings_activity_fragment_container,mLanguageFragment).commit();
        }else {
            mLanguageFragment = (LanguageFragment) getSupportFragmentManager().getFragment(savedInstanceState,LANGUAGE_FRAGMENT_KEY);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState,LANGUAGE_FRAGMENT_KEY,mLanguageFragment);
    }

    @OnClick(R.id.btn_done_language)
    void onDoneLanguageClicked(){
        runPreferenceLanguageProcess();
        Toast.makeText(getBaseContext(), R.string.restart_for_changes_message,Toast.LENGTH_LONG).show();
        finish();
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
}
