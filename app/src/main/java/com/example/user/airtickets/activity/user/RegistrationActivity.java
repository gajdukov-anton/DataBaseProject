package com.example.user.airtickets.activity.user;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.airtickets.R;
import com.example.user.airtickets.activity.admin.CreateFlightActivity;
import com.example.user.airtickets.api.retrofit.ServerApi;
import com.example.user.airtickets.models.ResponseFromServer;
import com.example.user.airtickets.models.User;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegistrationActivity extends AppCompatActivity {

    private boolean isButtonPassed = false;
    private Calendar calendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener dateListener;
    private User newUser = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    public void registrationNewUser(View view) {
        if (!isButtonPassed) {
            isButtonPassed = true;
            if (isOnline()) {
                //User newUser = new User();
                newUser.setFirstName(getStringFromEditText(R.id.nameEditText));
                newUser.setLastName(getStringFromEditText(R.id.secondNameEditText));
                newUser.setLogin(getStringFromEditText(R.id.newEmailEditText));
                newUser.setPassword(getStringFromEditText(R.id.newPasswordEditText));
                //newUser.setDateOfBirth(getStringFromEditText(R.id.dateOfBirthEditText));
                newUser.setSex(getSexFromRadioButton(R.id.male, R.id.female));
                if (newUser.isReadyToUpload()) {
                    postNewUserToServer(newUser);
                } else {
                    Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_SHORT).show();
                    isButtonPassed = false;
                }
            } else {
                isButtonPassed = false;
                Toast.makeText(RegistrationActivity.this, getResources().getString(R.string.online_error), Snackbar.LENGTH_LONG).show();
            }

        }
    }

    private String getStringFromEditText(int id) {
        EditText editText = (EditText) findViewById(id);
        return editText.getText().toString();
    }

    private void postNewUserToServer(final User user) {
        ServerApi serverApi = ServerApi.getInstance();
        ServerApi.RegistrationListener listener = new ServerApi.RegistrationListener() {
            @Override
            public void onNewUserUploaded(ResponseFromServer responseFromServer) {
                loadAuthorizationActivity(responseFromServer.status);
                isButtonPassed = false;
            }

        };
        serverApi.setRegistrationListener(listener);
        serverApi.uploadNewUserToServer(user, this);
    }

    private void loadAuthorizationActivity(String info) {
        if (info == null) {
            Toast.makeText(this, "Неизвестная ошибка сервера", Toast.LENGTH_SHORT).show();
        } else if (info.equals("user_added")) {
            this.finish();
        } else {
            Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
        }
    }

    public void setDateOfBirth(View v) {
        dateListener = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                newUser.setDateOfBirth(calendar.getTime());
                Locale local = new Locale("ru","RU");
                DateFormat df = DateFormat.getDateInstance(DateFormat.DEFAULT, local);
                setTextOnTextView(R.id.setDateOfBirthTextView, "Дата отлёта: " + df.format(calendar.getTime()));
            }

        };
        new DatePickerDialog(RegistrationActivity.this, dateListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    private void setTextOnTextView(int id, String value) {
        TextView textView = (TextView) findViewById(id);
        textView.setText(value);
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


    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
