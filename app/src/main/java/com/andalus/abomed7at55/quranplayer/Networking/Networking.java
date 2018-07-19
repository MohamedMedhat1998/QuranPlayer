package com.andalus.abomed7at55.quranplayer.Networking;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Networking {
    /**
     * A method to retrieve json response from a given url
     * @param url the target api
     * @return string json response
     * @throws IOException if the endpoint isn't available or is incorrect
     */
    public static String retrieveJson(String url) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
    }
}
