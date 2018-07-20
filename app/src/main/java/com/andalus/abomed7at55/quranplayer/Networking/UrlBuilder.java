package com.andalus.abomed7at55.quranplayer.Networking;

public class UrlBuilder {
    /**
     * This method builds the url of the selected language
     * @param language the desired language
     * @return url for the selected language
     */
    public static String buildLanguageUrl(String language){
        return "https://mp3quran.net/api/"+language+".json";
    }

    /**
     * This method builds the url of suras names based on the selected language
     * @param language the desired language
     * @return url for the selected language suras names
     */
    public static String buildSurasNamesUrl(String language){
        return "https://mp3quran.net/api/"+language+"_sura.json";
    }

    public static String buildStreamingUrl(String server,int suraId){
        String id = suraId+"";
        String leadingZeros;
        if(suraId>100){
            leadingZeros = "";
        }else if(suraId>10){
            leadingZeros = "0";
        }else{
            leadingZeros = "00";
        }
        return server+"/"+leadingZeros+id+".mp3";
    }
}
