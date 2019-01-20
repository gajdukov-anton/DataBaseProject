package com.example.user.airtickets.activity.admin;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.airtickets.R;
import com.example.user.airtickets.api.retrofit.ServerApi;
import com.example.user.airtickets.models.AdminData;
import com.example.user.airtickets.models.Class;
import com.example.user.airtickets.models.Company;
import com.example.user.airtickets.models.Plane;
import com.example.user.airtickets.models.ResponseFromServer;

import java.util.ArrayList;
import java.util.List;

public class AddPlaneActivity extends AppCompatActivity {

    private boolean isPressed = false;
    private List<String> nameCompanyList = getNameCompanyList(AdminData.getInstance().getCompanyList());
    private int currentIdCompany = AdminData.getInstance().getCompanyList().get(0).getIdCompany();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plane);
        createBackButton();
        updateTextView(R.id.idCompanyEditText, "Самолёт компании: " +  AdminData.getInstance().getCompanyList().get(0).getName());
        createSpinner(R.id.idCompanySpinner, nameCompanyList, new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentIdCompany = AdminData.getInstance().getCompanyList().get(i).getIdCompany();
                updateTextView(R.id.idCompanyEditText, "Самолёт компании: " +  AdminData.getInstance().getCompanyList().get(i).getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void createSpinner(int layoutId, final List<String> dataList, AdapterView.OnItemSelectedListener listener) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                dataList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) findViewById(layoutId);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(listener);
    }

    private void updateTextView(int id, String text) {
        TextView textView = (TextView) findViewById(id);
        textView.setText(text);
    }

    private void createBackButton() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private List<String> getNameCompanyList(List<Company> companies) {
        List<String> nameCompanies = new ArrayList<>();
        for (Company company : companies) {
            nameCompanies.add(company.getName());
        }
        return nameCompanies;
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


    public void createPlane(View view) {
        Plane plane = new Plane();
        EditText name = (EditText) findViewById(R.id.typePlaneEditText);
        plane.setType(name.getText().toString());
        plane.setIdCompany(currentIdCompany);
        if (plane.isReadyToUpload() && !isPressed) {
            if (!AdminData.getInstance().isPlaneExist(plane)) {
                isPressed = true;
                postNewPlaneToServer(plane);
            } else {
                Toast.makeText(this, "Такой самолёт уже есть", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_SHORT).show();
        }
    }

    private void postNewPlaneToServer(final Plane plane) {
        ServerApi serverApi = ServerApi.getInstance();
        ServerApi.AddPlaneListener listener = new ServerApi.AddPlaneListener() {
            @Override
            public void onSuccessful(ResponseFromServer responseFromServer) {
                Toast.makeText(AddPlaneActivity.this, responseFromServer.status, Toast.LENGTH_SHORT).show();
                if (responseFromServer.status.equals("added")) {
                    AdminData.getInstance().getPlaneList().add(plane);
                }
                isPressed = false;
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(AddPlaneActivity.this, message, Toast.LENGTH_SHORT).show();
                isPressed = false;
            }
        };
        serverApi.setAddPlaneListener(listener);
        serverApi.addPlane(plane);
    }
}
