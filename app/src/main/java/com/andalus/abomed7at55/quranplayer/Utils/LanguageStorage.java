package com.andalus.abomed7at55.quranplayer.Utils;

import com.andalus.abomed7at55.quranplayer.R;

public class LanguageStorage {
    public static final String PREFERENCE_LANGUAGE_KEY = "preferred_language";
    private static final String ARABIC = "_arabic";
    private static final String ENGLISH = "_english";
    private static final String FRENCH = "_france";
    private static final String RUSSIAN = "_russia";
    private static final String GERMAN = "_germany";
    private static final String SPANISH = "_spain";
    private static final String TURKISH = "_turkey";
    private static final String CHINESE = "_china";
    private static final String THAI = "_tahi";
    private static final String URDU = "_urdu";
    private static final String BENGALI = "_bengali";
    private static final String BOSNIAN = "_bosnia";
    private static final String INDONESIAN = "_indonesia";
    private static final String PORTUGUESE = "_portuguese";

    /**
     * This method is used to retrieve the id of a given language
     * @param language the language we want to get the id of the radio button attached to
     * @return radio button id
     */
    public static int getRadioButtonId(String language){
        int id = 0;
        switch (language){
            case ARABIC:
               id = R.id.rb_arabic;
               break;
            case ENGLISH:
                id = R.id.rb_english;
                break;
            case FRENCH:
                id = R.id.rb_french;
                break;
            case RUSSIAN:
                id = R.id.rb_russian;
                break;
            case GERMAN:
                id = R.id.rb_german;
                break;
            case SPANISH:
                id = R.id.rb_spanish;
                break;
            case TURKISH:
                id = R.id.rb_turkish;
                break;
            case CHINESE:
                id = R.id.rb_chinese;
                break;
            case THAI:
                id = R.id.rb_thai;
                break;
            case URDU:
                id = R.id.rb_urdu;
                break;
            case BENGALI:
                id = R.id.rb_bengali;
                break;
            case BOSNIAN:
                id = R.id.rb_bosnian;
                break;
            case INDONESIAN:
                id = R.id.rb_indonesian;
                break;
            case PORTUGUESE:
                id = R.id.rb_portuguese;
                break;
        }
        return id;
    }

    /**
     * This method is used to retrieve a language based on a given radio button id
     * @param radioButtonId the id of the radio button attached to the language we want
     * @return language string
     */
    public static String getLanguage(int radioButtonId){
        String selectedLanguage = null;
        switch (radioButtonId){
            case R.id.rb_arabic:
                selectedLanguage = ARABIC;
                break;
            case R.id.rb_english:
                selectedLanguage = ENGLISH;
                break;
            case R.id.rb_french:
                selectedLanguage = FRENCH;
                break;
            case R.id.rb_russian:
                selectedLanguage = RUSSIAN;
                break;
            case R.id.rb_german:
                selectedLanguage = GERMAN;
                break;
            case R.id.rb_spanish:
                selectedLanguage = SPANISH;
                break;
            case R.id.rb_turkish:
                selectedLanguage = TURKISH;
                break;
            case R.id.rb_chinese:
                selectedLanguage = CHINESE;
                break;
            case R.id.rb_thai:
                selectedLanguage = THAI;
                break;
            case R.id.rb_urdu:
                selectedLanguage = URDU;
                break;
            case R.id.rb_bengali:
                selectedLanguage = BENGALI;
                break;
            case R.id.rb_bosnian:
                selectedLanguage = BOSNIAN;
                break;
            case R.id.rb_indonesian:
                selectedLanguage = INDONESIAN;
                break;
            case R.id.rb_portuguese:
                selectedLanguage = PORTUGUESE;
                break;
        }
        return selectedLanguage;
    }
}
