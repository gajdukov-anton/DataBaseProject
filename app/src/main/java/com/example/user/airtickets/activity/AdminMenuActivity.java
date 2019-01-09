package com.example.user.airtickets.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.user.airtickets.R;
import com.example.user.airtickets.models.AdminData;

public class AdminMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
        AdminData.getInstance();
    }

    public void startMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void startCreateFlightActivity(View view) {
        Intent intent = new Intent(this, CreateFlightActivity.class);
        startActivity(intent);
    }

    public void startCreateTicketActivity(View view) {
        Intent intent = new Intent(this, ChooseFlightActivity.class);
        startActivity(intent);
    }
}
