package com.nathansass.happydolphin.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by nathansass on 11/11/16.
 */

public class InstagramGateway {
    public static void getMediaRoute(String accessToken, final ImageSearchListener listener){
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

    // BUGBUG: currently don't have support for this
    public static void likeMediaRoute(final Context context, String mediaID, String accessToken) {
        String baseUrl = "https://api.instagram.com/v1/media/" +  mediaID + "/likes";
        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();
        params.put("access_token", accessToken);

        client.post(baseUrl, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                // TODO: handle success when I get an authorized API
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(context, "Failure: No Communication With Database", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // BUGBUG: currently no support for this
    public static void unlikeMediaRoute(final Context context, String mediaID, String accessToken) {
        String baseUrl = "https://api.instagram.com/v1/media/" + mediaID + "/likes?access_token=" + accessToken;
        AsyncHttpClient client = new AsyncHttpClient();

        client.post(baseUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                // TODO: handle success when I get an authorized API
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(context, "Failure: No Communication With Database", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface ImageSearchListener {
        void done(JSONObject response);
    }

}
