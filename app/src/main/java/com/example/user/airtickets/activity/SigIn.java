package com.example.user.airtickets.activity;

import android.content.Context;
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

public class SigIn extends AppCompatActivity {

    Calendar dateAndTime = Calendar.getInstance();
    TextView currentDateTime;
    private boolean isPassed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sig_in);
    }

    public void sigIn(View view) {
        if (!isPassed) {
            isPassed = true;
            if (isOnline()) {
                NewUser newUser = new NewUser();
                EditText editText = (EditText) findViewById(R.id.nameEditText);
                newUser.name = editText.getText().toString();
                editText = (EditText) findViewById(R.id.secondNameEditText);
                newUser.second_name = editText.getText().toString();
                editText = (EditText) findViewById(R.id.newEmailEditText);
                newUser.login = editText.getText().toString();
                editText = (EditText) findViewById(R.id.newPasswordEditText);
                newUser.password = editText.getText().toString();
                editText = (EditText) findViewById(R.id.dateOfBirthEditText);
                newUser.date_of_birth = editText.getText().toString();
                //postNewUserToServer(newUser);
                finish();
            } else {
                isPassed = false;
                Toast.makeText(SigIn.this, getResources().getString(R.string.online_error), Snackbar.LENGTH_LONG).show();
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
                        Toast.makeText(SigIn.this, info.status, Snackbar.LENGTH_LONG).show();
                        loadAuthorization(info.status);
                    }
                } else {
                    Toast.makeText(SigIn.this, "Impossible to connect to server", Snackbar.LENGTH_LONG).show();
                }
                isPassed = false;
            }

            @Override
            public void onFailure(Call<ResponseFromServer> call, Throwable t) {
                isPassed = false;
            }
        });
    }

    private void loadAuthorization(String info) {
        if(info.equals("ok")) {

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
