package com.example.user.airtickets.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.airtickets.fragment.BookingDialogFragment;
import com.example.user.airtickets.R;
import com.example.user.airtickets.api.ServerApi;
import com.example.user.airtickets.adapter.TicketAdapter;
import com.example.user.airtickets.object.Flight;
import com.example.user.airtickets.object.Ticket;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BookingDialogFragment.BookingDialogListener {
    static public ServerApi serverApi;
    static private Retrofit retrofit;
    private List<Flight> flights = new ArrayList<>();
    private ArrayList<Ticket> tickets = new ArrayList<>();
    private TextView amountTicketInBooking;
    private int choosenFlight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setInitData();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        TicketAdapter adapter = new TicketAdapter(this, flights, null);
        TicketAdapter.Callback adapterListener = new TicketAdapter.Callback() {
            @Override
            public void onButtonClick(int position) {
                createDialogFragment(position);
            }

            @Override
            public void onRemoveItem(int position) {
                // Some actions.
            }
        };

        adapter.setCallback(adapterListener);
        recyclerView.setAdapter(adapter);

        amountTicketInBooking = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().
                findItem(R.id.nav_camera));
        initializeAmountTicketInBooking();
    }

    private void initializeAmountTicketInBooking() {
        amountTicketInBooking.setGravity(Gravity.CENTER_VERTICAL);
        amountTicketInBooking.setTypeface(null, Typeface.BOLD);
        amountTicketInBooking.setTextColor(getResources().getColor(R.color.colorAccent));
        amountTicketInBooking.setText("0");

    }

    private void updateAmountTicketInBooking(int amount) {
        amountTicketInBooking.setText(String.valueOf(amount));
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        EditText dialogName = dialog.getDialog().findViewById(R.id.dialog_name);
        EditText dialogSecondName = dialog.getDialog().findViewById(R.id.dialog_second_name);
        Toast.makeText(this, dialogName.getText().toString(), Toast.LENGTH_SHORT).show();
        tickets.add(new Ticket(dialogName.getText().toString(), dialogSecondName.getText().toString(), flights.get(choosenFlight)));
        updateAmountTicketInBooking(tickets.size());
    }


    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
    }

    public void createDialogFragment(int position) {
        this.choosenFlight = position;
        BookingDialogFragment dialog = new BookingDialogFragment();
        dialog.show(getSupportFragmentManager(), "custom");
    }

    public static void connectToServer() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://pure-taiga-64408.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        serverApi = retrofit.create(ServerApi.class);
    }

    private void setInitData() {
        flights.add(new Flight("Москва", "Казань", "Аэрофлот"));
        flights.add(new Flight("Омск", "Санкт-Петербург", "ТрансАэро"));
        flights.add(new Flight("Москва", "Киров", "Алапаевские Авиалинии"));
        flights.add(new Flight("Москва", "Челябинск", "Челябинский перелет"));
        flights.add(new Flight("Тагил", "Йошкар-Ола", "Тагильский улёт"));
        flights.add(new Flight("Тагил", "Йошкар-Ола", "Тагильский улёт"));
        flights.add(new Flight("Тагил", "Йошкар-Ола", "Тагильский улёт"));
        flights.add(new Flight("Тагил", "Йошкар-Ола", "Тагильский улёт"));

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadBookingActivity() {
        if (tickets.size() != 0) {
            Intent intent = new Intent(this, BookingActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("tickets", tickets);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Корзина пуста", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            loadBookingActivity();
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_exit_to_authorization) {
            Intent intent = new Intent(this, Authorization.class);
            startActivity(intent);
            this.finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
