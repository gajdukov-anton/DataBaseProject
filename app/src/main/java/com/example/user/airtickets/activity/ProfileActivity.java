package com.example.user.airtickets.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.airtickets.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        createBackButton();
        initData();
    }

    private void initData() {
        ImageView imageView = (ImageView) findViewById(R.id.imageProfile);
        imageView.setImageResource(R.drawable.no_photo);
        setStringToTextView(R.id.nameViewProfile, "Имя: Name");
        setStringToTextView(R.id.secondNameViewProfile, "Фамилия: SecondName");
        setStringToTextView(R.id.loginViewProfile, "Логин: Login");
        setStringToTextView(R.id.dateOfBirthViewProfile, "Дата рождения: DateOfBirth");
        setStringToTextView(R.id.sexViewProfile, "Пол: Sex");
    }

    private void setStringToTextView(int id, String value) {
        TextView textView = (TextView) findViewById(id);
        textView.setText(value);
    }

    private void createBackButton() {
        ActionBar actionBar =getSupportActionBar();
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
}
