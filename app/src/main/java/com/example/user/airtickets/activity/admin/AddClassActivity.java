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
import com.example.user.airtickets.models.Airport;
import com.example.user.airtickets.models.Class;
import com.example.user.airtickets.models.ResponseFromServer;

public class AddClassActivity extends AppCompatActivity {

    private boolean isPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
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


    public void createClass(View view) {
        Class newClass = new Class();
        EditText name = (EditText) findViewById(R.id.nameClassEditText);
        EditText description = (EditText) findViewById(R.id.descriptionClassEditText);
        newClass.setName(name.getText().toString());
        newClass.setDescription(description.getText().toString());
        if (newClass.isReadyToUpload() && !isPressed) {
            if (!AdminData.getInstance().isClassExist(newClass)) {
                isPressed = true;
                postNewClassToServer(newClass);
            } else {
                Toast.makeText(this, "Такой класс уже есть", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_SHORT).show();
        }
    }

    private void postNewClassToServer(final Class newClass) {
        ServerApi serverApi = ServerApi.getInstance();
        ServerApi.AddClassListener listener = new ServerApi.AddClassListener() {
            @Override
            public void onSuccessful(ResponseFromServer responseFromServer) {
                Toast.makeText(AddClassActivity.this, responseFromServer.status, Toast.LENGTH_SHORT).show();
                if (responseFromServer.status.equals("added")) {
                    AdminData.getInstance().getClassList().add(newClass);
                }
                isPressed = false;
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(AddClassActivity.this, message, Toast.LENGTH_SHORT).show();
                isPressed = false;
            }
        };
        serverApi.setAddClassListener(listener);
        serverApi.addClass(newClass);
    }
}
