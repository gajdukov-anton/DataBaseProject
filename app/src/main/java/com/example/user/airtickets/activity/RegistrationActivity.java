package com.example.user.airtickets.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.airtickets.R;
import com.example.user.airtickets.object.ResponseFromServer;
import com.example.user.airtickets.object.NewUser;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    Calendar dateAndTime = Calendar.getInstance();
    TextView currentDateTime;
    private boolean isPassed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    public void sigIn(View view) {
        if (!isPassed) {
            isPassed = true;
            if (isOnline()) {
                NewUser newUser = new NewUser();
                EditText editText = (EditText) findViewById(R.id.nameEditText);
                newUser.setName(editText.getText().toString());
                editText = (EditText) findViewById(R.id.secondNameEditText);
                newUser.setSecond_name(editText.getText().toString());
                editText = (EditText) findViewById(R.id.newEmailEditText);
                newUser.setLogin(editText.getText().toString());
                editText = (EditText) findViewById(R.id.newPasswordEditText);
                newUser.setPassword(editText.getText().toString());
                editText = (EditText) findViewById(R.id.dateOfBirthEditText);
                newUser.setDate_of_birth(editText.getText().toString());
                editText = (EditText) findViewById(R.id.sexEditText);
                newUser.setSex(editText.getText().toString());
                //postNewUserToServer(newUser);
                finish(); //не забыть убрать
            } else {
                isPassed = false;
                Toast.makeText(RegistrationActivity.this, getResources().getString(R.string.online_error), Snackbar.LENGTH_LONG).show();
            }

        }
    }

    private void postNewUserToServer(NewUser user) {
        Call<ResponseFromServer> call = MainActivity.serverApi.postNewUser(user);
        call.enqueue(new Callback<ResponseFromServer>() {
            @Override
            public void onResponse(Call<ResponseFromServer> call, Response<ResponseFromServer> response) {
                if (response.isSuccessful()) {
                    ResponseFromServer info = response.body();
                    if (info != null) {
                        Toast.makeText(RegistrationActivity.this, info.status, Snackbar.LENGTH_LONG).show();
                        loadAuthorizationActivity(info.status);
                    }
                } else {
                    Toast.makeText(RegistrationActivity.this, "Impossible to connect to server", Snackbar.LENGTH_LONG).show();
                }
                isPassed = false;
            }

            @Override
            public void onFailure(Call<ResponseFromServer> call, Throwable t) {
                isPassed = false;
            }
        });
    }

    private void loadAuthorizationActivity(String info) {
        if(info.equals("ok")) {
            Intent intent = new Intent(this, Authorization.class);
            startActivity(intent);
            this.finish();
        }

    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private boolean checkDateOfBirth(String date) {
        return true;
    }
}
