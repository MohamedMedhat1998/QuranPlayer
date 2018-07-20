package com.andalus.abomed7at55.quranplayer.Utils;

import com.andalus.abomed7at55.quranplayer.Networking.UrlBuilder;
import com.andalus.abomed7at55.quranplayer.Objects.Sheekh;
import com.andalus.abomed7at55.quranplayer.Objects.Sura;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonParser {

    private static final String RECITERS_KEY = "reciters";
    private static final String ID_KEY = "id";
    private static final String NAME_KEY = "name";
    private static final String SERVER_KEY = "Server";
    private static final String REWAYA_KEY = "rewaya";
    private static final String COUNT_KEY = "count";
    private static final String SURAS_KEY = "suras";

    private static final String SURAS_NAME_KEY = "Suras_Name";

    public static ArrayList<Sheekh> parseSheekhs(String data) throws JSONException {
        JSONObject rootObject = new JSONObject(data);
        JSONArray recitersArray = rootObject.getJSONArray(RECITERS_KEY);
        int n = recitersArray.length();
        long id;
        String name,server,rewaya,suras;
        int count;
        ArrayList<Sheekh> sheekhArrayList = new ArrayList<>();
        for(int i = 0; i < n ; i++){
            id = Long.parseLong(recitersArray.getJSONObject(i).getString(ID_KEY));
            name = recitersArray.getJSONObject(i).getString(NAME_KEY);
            server = recitersArray.getJSONObject(i).getString(SERVER_KEY);
            rewaya = recitersArray.getJSONObject(i).getString(REWAYA_KEY);
            count = Integer.parseInt(recitersArray.getJSONObject(i).getString(COUNT_KEY));
            suras = recitersArray.getJSONObject(i).getString(SURAS_KEY);
            sheekhArrayList.add(new Sheekh(id,name,server,rewaya,count,suras));
        }
        return sheekhArrayList;
    }

    public static ArrayList<Sura> parseSura(String namesData, ArrayList<Integer> ids, String streamingServerRoot) throws JSONException {
        JSONObject rootObject = new JSONObject(namesData);
        JSONArray namesArray = rootObject.getJSONArray(SURAS_NAME_KEY);
        int n = ids.size();
        String tempStringId;
        int tempId;
        int it = 0;
        ArrayList<Sura> suras = new ArrayList<>();
        for (int i = 0 ; i < n ;i++){
            tempId = ids.get(i);
            tempStringId = tempId + "";
            while(!tempStringId.equals(namesArray.getJSONObject(it).getString(ID_KEY))){
                it++;
            }
            suras.add(new Sura(tempId,namesArray.getJSONObject(it).getString(NAME_KEY), UrlBuilder.buildStreamingUrl(streamingServerRoot,tempId)));
        }
        return suras;
    }

}
