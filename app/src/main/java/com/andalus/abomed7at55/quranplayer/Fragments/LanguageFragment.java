package com.andalus.abomed7at55.quranplayer.Fragments;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.andalus.abomed7at55.quranplayer.R;
import com.andalus.abomed7at55.quranplayer.Utils.LanguageStorage;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LanguageFragment extends Fragment {
    @BindView(R.id.rg_language_selection)
    RadioGroup rgLanguages;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_language,container,false);
        ButterKnife.bind(this,view);
        String language = PreferenceManager.getDefaultSharedPreferences(view.getContext()).getString(LanguageStorage.PREFERENCE_LANGUAGE_KEY,null);
        rgLanguages.check(LanguageStorage.getRadioButtonId(language));
        return view;
    }
}
