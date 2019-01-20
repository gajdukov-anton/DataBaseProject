package com.example.user.airtickets.activity.admin;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.user.airtickets.R;
import com.example.user.airtickets.api.retrofit.ServerApi;
import com.example.user.airtickets.models.AdminData;
import com.example.user.airtickets.models.Airport;
import com.example.user.airtickets.models.FlightForUpload;
import com.example.user.airtickets.models.Plane;
import com.example.user.airtickets.models.ResponseFromServer;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CreateFlightActivity extends AppCompatActivity {

    private boolean isButtonPassed = false;
    private List<String> namePlanesList = getNamePlanesList(AdminData.getInstance().getPlaneList());
    private List<String> nameAirportsList = getNameAirportsList(AdminData.getInstance().getAirportList());
    private List<String> pointOfDestList = getPointOfDestList(AdminData.getInstance().getAirportList());
    private int currentIdPlane = AdminData.getInstance().getPlaneList().get(0).getIdPlane();
    private int currentIdAirport = AdminData.getInstance().getAirportList().get(0).getIdAirport();
    private String currentPointOfDest = AdminData.getInstance().getAirportList().get(0).getLocation();
    private Calendar calendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener dateListener;
    private TimePickerDialog.OnTimeSetListener timeListener;
    private FlightForUpload flight = new FlightForUpload();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_flight);
        createBackButton();
        createSpinner(R.id.idAirportSpinner, nameAirportsList, new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentIdAirport = AdminData.getInstance().getAirportList().get(position).getIdAirport();
                updateTextView(R.id.pointOfDepEditText, "Город отправления: " + AdminData.getInstance().getAirportList().get(position).getLocation());
                flight.setPointOfDeparture(AdminData.getInstance().getAirportList().get(position).getLocation());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        createSpinner(R.id.idPlaneSpinner, namePlanesList, new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Plane plane = AdminData.getInstance().getPlaneList().get(position);
                currentIdPlane = plane.getIdPlane();
                updateTextView(R.id.idPlainEditText, "Самолёт рейса компании " + AdminData.getInstance().getCompanyNameByPlane(plane) + ":");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
//        createSpinner(R.id.namePointOfDestSpinner, pointOfDestList, new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                currentPointOfDest = AdminData.getInstance().getAirportList().get(position).getLocation();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
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

    private List<String> getNamePlanesList(List<Plane> planes) {
        List<String> namePlanes = new ArrayList<>();
        for (Plane plane : planes) {
            namePlanes.add(plane.getType());
        }
        return namePlanes;
    }

    private List<String> getNameAirportsList(List<Airport> airports) {
        List<String> nameAirports = new ArrayList<>();
        for (Airport airport : airports) {
            nameAirports.add(airport.getName());
        }
        return nameAirports;
    }

    private List<String> getPointOfDestList(List<Airport> airports) {
        List<String> pointOfDestList = new ArrayList<>();
        for (Airport airport : airports) {
            pointOfDestList.add(airport.getLocation());
        }
        return pointOfDestList;
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

    public void createNewFlight(View view) {
        if (!isButtonPassed) {
            if (isOnline()) {
                flight.setIdAirport(currentIdAirport);
                flight.setIdPlane(currentIdPlane);
                flight.setPointOfDestination(getValueFromEditText(R.id.pointOfDestEditText));
                if (isFlightReadyToUpload(flight)) {
                    postNewFlightToServer(flight);
                } else {
                   // Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_SHORT).show();
                    isButtonPassed = false;
                }
            } else {
                isButtonPassed = false;
                Toast.makeText(CreateFlightActivity.this, getResources().getString(R.string.online_error), Snackbar.LENGTH_LONG).show();
            }
        }

    }

    private boolean isFlightReadyToUpload(FlightForUpload flight) {
        if (!flight.isAllMethodsFilled()) {
            Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (flight.getPointOfDeparture().toLowerCase().equals(flight.getPointOfDestination().toLowerCase())) {
            Toast.makeText(this, "Город отправления совпадает с городом прибытия", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (flight.getTimeOfDeparture().after(flight.getTimeOfDestination())
                || flight.getTimeOfDestination().equals(flight.getTimeOfDeparture())) {
            Toast.makeText(this, "Дата прибытия должна быть позже даты отправления", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
        //if (flight.getTimeOfDeparture() >= flight.getTimeOfDestination())
    }

    private String getValueFromEditText(int id) {
        EditText editText = (EditText) findViewById(id);
        return editText.getText().toString();
    }

    private String getValueFromTextView(int id) {
        TextView textView = (TextView) findViewById(id);
        return textView.getText().toString();
    }

    public void setDateOfDep(View v) {
        dateListener = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                flight.setTimeOfDeparture(calendar.getTime());
                Locale local = new Locale("ru","RU");
                DateFormat df = DateFormat.getDateInstance(DateFormat.DEFAULT, local);
                setTextOnTextView(R.id.setDataOfDepTextView, "Дата отлёта: " + df.format(calendar.getTime()));
            }

        };
        new DatePickerDialog(CreateFlightActivity.this, dateListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    public void setTimeOfDep(View v) {
        timeListener = new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                flight.setTimeOfDeparture(calendar.getTime());
                Locale local = new Locale("ru","RU");
                DateFormat df = DateFormat.getTimeInstance(DateFormat.DEFAULT, local);
                setTextOnTextView(R.id.setTimeOfDepTextView, "Время отлёта: " + df.format(calendar.getTime()));
            }
        };
        new TimePickerDialog(CreateFlightActivity.this, timeListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), true)
                .show();
    }

    public void setDateOfDest(View v) {
        dateListener = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                flight.setTimeOfDestination(calendar.getTime());
                Locale local = new Locale("ru","RU");
                DateFormat df = DateFormat.getDateInstance(DateFormat.DEFAULT, local);
                setTextOnTextView(R.id.setDataOfDestTextView, "Дата прилёта: " + df.format(calendar.getTime()));
            }

        };
        new DatePickerDialog(CreateFlightActivity.this, dateListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    public void setTimeOfDest(View v) {
        timeListener = new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                flight.setTimeOfDestination(calendar.getTime());
                Locale local = new Locale("ru","RU");
                DateFormat df = DateFormat.getTimeInstance(DateFormat.DEFAULT, local);
                setTextOnTextView(R.id.setTimeOfDestTextView, "Время прилёта: " + df.format(calendar.getTime()));
            }
        };
        new TimePickerDialog(CreateFlightActivity.this, timeListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), true)
                .show();
    }

    private void setTextOnTextView(int id, String value) {
        TextView textView = (TextView) findViewById(id);
        textView.setText(value);
    }

    private void postNewFlightToServer(FlightForUpload flight) {
        ServerApi serverApi = ServerApi.getInstance();
        ServerApi.UploadNewFlightListener listener = new ServerApi.UploadNewFlightListener() {
            @Override
            public void onUploadNewFlight(ResponseFromServer responseFromServer) {
                Toast.makeText(CreateFlightActivity.this, responseFromServer.status, Toast.LENGTH_SHORT).show();
                isButtonPassed = false;
            }

            @Override
            public void onFailure(String message) {
                isButtonPassed = false;
                Toast.makeText(CreateFlightActivity.this, "Не удалось: " + message, Toast.LENGTH_SHORT).show();
            }
        };
        serverApi.setUploadNewFlightListener(listener);
        serverApi.uploadNewFlightToServer(flight);
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
