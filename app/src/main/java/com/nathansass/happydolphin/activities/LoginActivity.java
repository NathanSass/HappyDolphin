package com.nathansass.happydolphin.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.nathansass.happydolphin.R;

public class LoginActivity extends AppCompatActivity {
    Button btnSignIn;
    SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkForInstagramData();

        btnSignIn = (Button)findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithInstagram();
            }
        });
    }

    private void signInWithInstagram() {
        final Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme("https")
                .authority("api.instagram.com")
                .appendPath("oauth")
                .appendPath("authorize")
                .appendQueryParameter("client_id", "9693563824bb4bb9a55f07c6f0f0f080")
                .appendQueryParameter("redirect_uri", "https://github.com/nathansass")
                .appendQueryParameter("response_type", "token");
        final Intent browser = new Intent(Intent.ACTION_VIEW, uriBuilder.build());
        startActivity(browser);
    }

    private void checkForInstagramData() {
        final Uri data = this.getIntent().getData();

        if(data != null && data.getScheme().equals("https") && data.getFragment() != null) {
            final String accessToken = data.getFragment().replaceFirst("access_token=", "");
            if (accessToken != null) {
                handleSignInResult(accessToken);
            } else {
                handleSignInResult(null);
            }
        }
    }

    private void handleSignInResult(String accessToken) {
        if(accessToken == null) {
            /* Login error */
            Toast.makeText(getApplicationContext(), R.string.login_error, Toast.LENGTH_SHORT).show();
        } else {
            /* Login success */
            pref = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor edit = pref.edit();
            edit.putString(R.string.access_token + "", accessToken);
            edit.commit();

            Intent i = new Intent(this, ImageStreamActivity.class);
            startActivity(i);
        }
    }

}
