package com.nathansass.happydolphin.network;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by nathansass on 11/11/16.
 */

public class ImageSearchGateway {
    public static void getRoute(String search, String accessToken, final ImageSearchListener listener){
        String baseUrl = "https://api.instagram.com/v1/users/self/media/recent/?access_token=" + accessToken ;
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(baseUrl, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                listener.done(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("error", String.valueOf(errorResponse));
            }
        });
    }


    public interface ImageSearchListener {
        void done(JSONObject response);
    }

}
