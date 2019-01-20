package com.example.user.airtickets.activity.admin;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.airtickets.R;
import com.example.user.airtickets.api.retrofit.ServerApi;
import com.example.user.airtickets.models.AdminData;
import com.example.user.airtickets.models.Class;
import com.example.user.airtickets.models.Company;
import com.example.user.airtickets.models.ResponseFromServer;

public class AddCompanyActivity extends AppCompatActivity {

    private boolean isPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);
        createBackButton();
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


    public void createCompany(View view) {
        Company company = new Company();
        EditText name = (EditText) findViewById(R.id.nameCompanyEditText);
        EditText rating = (EditText) findViewById(R.id.ratingCompanyEditText);
        if (isValidRating(rating.getText().toString())) {
            company.setName(name.getText().toString());
            company.setRating(Double.valueOf(rating.getText().toString()));
            if (company.isReadyToUpload() && !isPressed) {
                if (!AdminData.getInstance().isCompanyExist(company)) {
                    isPressed = true;
                    postNewCompanyToServer(company);
                } else {
                    Toast.makeText(this, "Такая компания уже есть", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Рейтинг может быть только дробным числом от 0 до 100", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidRating(String ratingStr) {
        double rating;
        try {
            rating = Double.valueOf(ratingStr);
        } catch (NumberFormatException e) {
            return false;
        }
        return rating >= 0 && rating <= 100;
    }

    private void postNewCompanyToServer(final Company company) {
        ServerApi serverApi = ServerApi.getInstance();
        ServerApi.AddCompanyListener listener = new ServerApi.AddCompanyListener() {
            @Override
            public void onSuccessful(ResponseFromServer responseFromServer) {
                if (responseFromServer.status.equals("added")) {
                    AdminData.getInstance().getCompanyList().add(company);
                }
                Toast.makeText(AddCompanyActivity.this, responseFromServer.status, Toast.LENGTH_SHORT).show();
                isPressed = false;
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(AddCompanyActivity.this, message, Toast.LENGTH_SHORT).show();
                isPressed = false;
            }
        };
        serverApi.setAddCompanyListener(listener);
        serverApi.addCompany(company);
    }
}
