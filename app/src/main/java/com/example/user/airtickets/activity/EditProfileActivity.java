package com.example.user.airtickets.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.user.airtickets.R;
import com.example.user.airtickets.api.retrofit.ServerApi;
import com.example.user.airtickets.models.ResponseFromServer;
import com.example.user.airtickets.models.User;
import com.example.user.airtickets.models.UserData;

public class EditProfileActivity extends AppCompatActivity {

    private User user;
    private boolean isButtonPassed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Bundle bundle = getIntent().getExtras();
        user = bundle.getParcelable("User");
        createBackButton();
        setInformationToEditText();
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void postEditUserData(View view) {
        if (!isButtonPassed) {
            isButtonPassed = true;
            if (isOnline()) {
                editUserDataFromEditText();
                user.setPassword(UserData.currentPassword);
                postEditUserToServer(user);
            } else {
                isButtonPassed = false;
                Toast.makeText(EditProfileActivity.this, getResources().getString(R.string.online_error), Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private void setInformationToEditText() {
        setValueToEditText(R.id.nameEditText, user.getFirstName());
        setValueToEditText(R.id.secondNameEditText, user.getLastName());
    }

    private void setValueToEditText(int id, String value) {
        EditText editText = (EditText) findViewById(id);
        editText.setText(value);
    }

    private void editUserDataFromEditText() {
        user.editFirstName(getStringFromEditText(R.id.nameEditText));
        user.editLastName(getStringFromEditText(R.id.secondNameEditText));
        user.editNewPassword(getStringFromEditText(R.id.newPasswordEditText));
        user.editSex(getSexFromRadioButton(R.id.male, R.id.female));
    }

    private String getStringFromEditText(int id) {
        EditText editText = (EditText) findViewById(id);
        return editText.getText().toString();
    }

    private String getSexFromRadioButton(int idMale, int idFemale) {
        RadioButton maleButton = (RadioButton) findViewById(idMale);
        RadioButton femaleButton = (RadioButton) findViewById(idFemale);
        if (maleButton.isChecked()) {
            return "male";
        } else if (femaleButton.isChecked()) {
            return "female";
        } else {
            return "";
        }
    }

    private void postEditUserToServer(User user) {
        ServerApi serverApi = ServerApi.getInstance();
        Toast.makeText(EditProfileActivity.this, "Besit", Toast.LENGTH_SHORT).show();
        ServerApi.EditUserListener listener = new ServerApi.EditUserListener() {
            @Override
            public void onUploadEditUser(ResponseFromServer responseFromServer) {
                isButtonPassed = false;
                finish();
            }

            @Override
            public void onFailure(String require) {
                Toast.makeText(EditProfileActivity.this, require, Toast.LENGTH_SHORT).show();
                isButtonPassed = false;
            }
        };
        serverApi.setEditUserListener(listener);
        serverApi.uploadEditUserToServer(user, this);
    }

    private void createBackButton() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void finish() {
        Intent data = new Intent();
        data.putExtra(ProfileActivity.ACCESS_MESSAGE_PROFILE_ACTIVITY, user);
        setResult(RESULT_OK, data);
        super.finish();
    }

}
