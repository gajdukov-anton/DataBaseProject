package com.example.user.airtickets.activity.user;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.airtickets.R;
import com.example.user.airtickets.activity.admin.AdminMenuActivity;
import com.example.user.airtickets.api.retrofit.ServerApi;
import com.example.user.airtickets.models.ResponseFromServer;
import com.example.user.airtickets.models.UserData;

import java.util.ArrayList;

public class Authorization extends AppCompatActivity {
    private final static String TAG = "Authorization";
    private boolean isPassed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
    }

    public void startMainActivity(View view) {
        if (!isPassed) {
            isPassed = true;
            EditText emailEditText = (EditText) findViewById(R.id.emailEditText);
            EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);
            UserData user = new UserData(emailEditText.getText().toString(), passwordEditText.getText().toString());
            UserData.currentLogin = user.login;
            UserData.currentPassword = user.password;
            UserData.bookedTickets = new ArrayList<>();
            //UserData.currentIdUser = user.get
            if (isOnline()) {
                authenticateUser(user);
            } else {
                isPassed = false;
                Toast.makeText(Authorization.this, getResources().getString(R.string.online_error), Snackbar.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    public void startSigInActivity(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    private void authenticateUser(UserData user) {
        ServerApi serverApi = ServerApi.getInstance();
        ServerApi.AuthorizationListener listener = new ServerApi.AuthorizationListener() {
            @Override
            public void onAuthenticatedUser(ResponseFromServer responseFromServer) {
                loadMainActivity(responseFromServer.status);
                isPassed = false;
            }

            @Override
            public void onFailure(String response) {
                Toast.makeText(Authorization.this, response, Toast.LENGTH_SHORT).show();
                isPassed = false;
            }
        };
        serverApi.setAuthorizationListener(listener);
        serverApi.uploadUserDataToServer(user, this);
    }

    private void loadMainActivity(String userType) {
        if (userType.equals("user")) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("userType", userType);
            startActivity(intent);
            this.finish();
        } else if (userType.equals("admin")) {
            Intent intent = new Intent(this, AdminMenuActivity.class);
            intent.putExtra("userType", userType);
            startActivity(intent);
            this.finish();
        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.login_error), Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
