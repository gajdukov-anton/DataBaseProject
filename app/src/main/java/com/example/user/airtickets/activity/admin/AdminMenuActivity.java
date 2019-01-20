package com.example.user.airtickets.activity.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.user.airtickets.R;
import com.example.user.airtickets.activity.user.MainActivity;
import com.example.user.airtickets.models.AdminData;

public class AdminMenuActivity extends AppCompatActivity {

    private static final String DATA_DOWNLOAD_ERROR = "Редактирование временно невозможно, идёт подгрузка данных";

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

    public void startCreateFlight(View view) {
        if (!AdminData.getInstance().getAirportList().isEmpty() && !AdminData.getInstance().getPlaneList().isEmpty()) {
            Intent intent = new Intent(this, CreateFlightActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, DATA_DOWNLOAD_ERROR, Toast.LENGTH_SHORT).show();
        }
    }

    public void startCreateTicket(View view) {
        if (!AdminData.getInstance().getClassList().isEmpty()) {
            Intent intent = new Intent(this, ChooseFlightActivity.class);
            intent.putExtra("Action", "CREATE_TICKET");
            startActivity(intent);
        } else {
            Toast.makeText(this, DATA_DOWNLOAD_ERROR, Toast.LENGTH_SHORT).show();
        }
    }

    public void startDeleteFlight(View view) {
        Intent intent = new Intent(this, DeleteFlightActivity.class);
        startActivity(intent);
    }

    public void startDeleteTicket(View view) {
        if (!AdminData.getInstance().getClassList().isEmpty()) {
            Intent intent = new Intent(this, ChooseFlightActivity.class);
            intent.putExtra("Action", "DELETE_TICKET");
            startActivity(intent);
        } else {
            Toast.makeText(this, DATA_DOWNLOAD_ERROR, Toast.LENGTH_SHORT).show();
        }

    }

    public void startAllFlightsActivity(View view) {
        Intent intent = new Intent(this, AllFlightsActivity.class);
        startActivity(intent);
    }

    public void startAddAirportActivity(View view) {
        if (!AdminData.getInstance().getAirportList().isEmpty()) {
            Intent intent = new Intent(this, AddAirportActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, DATA_DOWNLOAD_ERROR, Toast.LENGTH_SHORT).show();
        }
    }

    public void startAddClassActivity(View view) {
        if (!AdminData.getInstance().getClassList().isEmpty()) {
            Intent intent = new Intent(this, AddClassActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, DATA_DOWNLOAD_ERROR, Toast.LENGTH_SHORT).show();
        }
    }

    public void startAddCompanyActivity(View view) {
        if (!AdminData.getInstance().getCompanyList().isEmpty()) {
            Intent intent = new Intent(this, AddCompanyActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, DATA_DOWNLOAD_ERROR, Toast.LENGTH_SHORT).show();
        }
    }

    public void startAddPlaneActivity(View view) {
        if (!AdminData.getInstance().getPlaneList().isEmpty()) {
            Intent intent = new Intent(this, AddPlaneActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, DATA_DOWNLOAD_ERROR, Toast.LENGTH_SHORT).show();
        }
    }
}
