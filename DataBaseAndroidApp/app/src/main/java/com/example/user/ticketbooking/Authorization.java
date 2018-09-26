package com.example.user.ticketbooking;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Authorization extends AppCompatActivity {
    private final static String TAG = "Authorization";
    private boolean isPassed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        MainActivity.connectToServer();
    }

    public void startMainActivity(View view) {
        if (!isPassed) {
            isPassed = true;
            EditText emailEditText = (EditText) findViewById(R.id.emailEditText);
            EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);
            if (isOnline()) {
                authenticateUser(emailEditText.getText().toString(), passwordEditText.getText().toString());
            } else {
                isPassed = false;
                Toast.makeText(Authorization.this, getResources().getString(R.string.online_error), Snackbar.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    public void startSigInActivity(View view) {
        Intent intent = new Intent(this, SigIn.class);
        startActivity(intent);
    }


    private void authenticateUser(String login, String password) {
        final User user = new User();
        user.login = login;
        user.password = password;
        Call<ResponseFromServer> call = MainActivity.serverApi.postBook(user);
        call.enqueue(new Callback<ResponseFromServer>() {
            @Override
            public void onResponse(Call<ResponseFromServer> call, Response<ResponseFromServer> response) {
                if (response.isSuccessful()) {
                    ResponseFromServer info = response.body();
                    if (info != null) {
                        loadMainActivity(info.status);
                        Toast.makeText(Authorization.this,  info.status, Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Authorization.this, "Impossible to connect to server" + user.login, Snackbar.LENGTH_LONG).show();
                }
                isPassed = false;
            }

            @Override
            public void onFailure(Call<ResponseFromServer> call, Throwable t) {
                isPassed = false;
            }
        });
    }
    
    private void loadMainActivity(String userType) {
        if (userType.equals("admin") || userType.equals("unknown")) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("userType", userType);
            startActivity(intent);
            this.finish();
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.login_error), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
