package com.example.user.airtickets.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.airtickets.R;
import com.example.user.airtickets.api.retrofit.ServerApi;
import com.example.user.airtickets.object.User;
import com.example.user.airtickets.object.UserData;

public class ProfileActivity extends AppCompatActivity {

    private User user;
    private boolean isDownloaded = false;
    public static final int REQUEST_ACCESS_TO_EDIT_PROFILE_ACTIVITY = 1;
    static final String ACCESS_MESSAGE_PROFILE_ACTIVITY = "ACCESS_MESSAGE_PROFILE_ACTIVITY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        createBackButton();
        initData();
    }

    private void initData() {
        ServerApi serverApi = ServerApi.getInstance();
        ServerApi.DownloadUserListener listener = new ServerApi.DownloadUserListener() {
            @Override
            public void onDownloadUser(User user) {
                user.setLogin(UserData.currentLogin);
                user.setPassword(UserData.currentPassword);
                user.setNewPassword(UserData.currentPassword);
                displayUserData(user);
                isDownloaded = true;
            }

        };
        serverApi.setDownloadUserListener(listener);
        UserData userData = new UserData(UserData.currentLogin, UserData.currentPassword);
        serverApi.downloadUser(this, userData);

        ImageView imageView = (ImageView) findViewById(R.id.imageProfile);
        imageView.setImageResource(R.drawable.no_photo);
    }

    private void displayUserData(@Nullable User user) {
        this.user = user;
        setStringToTextView(R.id.nameViewProfile, "Имя:  " + user.getFirstName());
        setStringToTextView(R.id.secondNameViewProfile, "Фамилия: " + user.getLastName());
        setStringToTextView(R.id.loginViewProfile, "Логин: " + user.getLogin());
        setStringToTextView(R.id.dateOfBirthViewProfile, "Дата рождения: " + user.getDateOfBirth());
        setStringToTextView(R.id.sexViewProfile, "Пол: " + user.getSex());
    }

    public void loadEditProfileActivity(View view) {
        if (isDownloaded) {
            Intent intent = new Intent(this, EditProfileActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("User", user);
            intent.putExtras(bundle);
            startActivityForResult(intent, REQUEST_ACCESS_TO_EDIT_PROFILE_ACTIVITY);
        }
    }

    private void setStringToTextView(int id, String value) {
        TextView textView = (TextView) findViewById(id);
        textView.setText(value);
    }

    private void createBackButton() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ACCESS_TO_EDIT_PROFILE_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                try {
                    user = bundle.getParcelable(ACCESS_MESSAGE_PROFILE_ACTIVITY);
                    displayUserData(user);
                } catch (NullPointerException e) {
                    Toast.makeText(this, "Не удалось отобразить новые данные", Toast.LENGTH_SHORT).show();
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
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
}
