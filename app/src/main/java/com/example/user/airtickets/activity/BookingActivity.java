package com.example.user.airtickets.activity;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.airtickets.R;
import com.example.user.airtickets.adapter.BookingAdapter;
import com.example.user.airtickets.api.retrofit.ServerApi;
import com.example.user.airtickets.fragment.PostBookingDialogFragment;
import com.example.user.airtickets.models.Booking;
import com.example.user.airtickets.models.ResponseFromServer;
import com.example.user.airtickets.models.Ticket;
import com.example.user.airtickets.models.UserData;


import java.util.ArrayList;

public class BookingActivity extends AppCompatActivity implements PostBookingDialogFragment.PostBookingDialogListener {

    private ArrayList<Ticket> tickets = new ArrayList<>();
    private Booking booking = new Booking();
    private ServerApi serverApi = ServerApi.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        Bundle bundle = getIntent().getExtras();
        tickets = bundle.getParcelableArrayList("tickets");
        booking.setTickets(tickets);
        booking.setLogin(UserData.currentLogin);
        booking.setPassword(UserData.currentPassword);
        booking.setTickets(tickets);
        createRecyclerViewWithTickets();
        createBackButton();
    }

    private void createBackButton() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void createRecyclerViewWithTickets() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listBooking);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        BookingAdapter adapter = new BookingAdapter(this, tickets, null);
        BookingAdapter.Callback adapterListener = new BookingAdapter.Callback() {
            @Override
            public void onButtonRemoveClick(int position) {
                tickets.remove(position);
                createRecyclerViewWithTickets();
            }

            @Override
            public void onRemoveItem(int position) {
                // Some actions.
            }
        };
        adapter.setCallback(adapterListener);
        recyclerView.setAdapter(adapter);
    }

    public void sendBooking(View view) {
        if (tickets.size() > 0) {
            Toast.makeText(this, booking.getTickets().get(0).getSex(), Toast.LENGTH_LONG).show();
            ServerApi.PostBookingListener listener = new ServerApi.PostBookingListener() {
                @Override
                public void onUploadBooking(ResponseFromServer responseFromServer) {
                    Toast.makeText(BookingActivity.this, responseFromServer.status, Toast.LENGTH_SHORT).show();
                    tickets.clear();
                    finish();
                }

                @Override
                public void onFailure(String request) {
                    Toast.makeText(BookingActivity.this, "Не удалось: " + request, Toast.LENGTH_LONG).show();
                }
            };
            serverApi.setPostBookingListener(listener);
            createDialogFragment();
        } else {
            Toast.makeText(this, "Корзина пуста", Toast.LENGTH_SHORT).show();
        }
    }


    public void createDialogFragment() {
        PostBookingDialogFragment dialog = new PostBookingDialogFragment();
        dialog.show(getSupportFragmentManager(), "custom");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        EditText numberCard = dialog.getDialog().findViewById(R.id.dialog_number_card);
        booking.setCard(numberCard.getText().toString());
        serverApi.postBookingToServer(booking);
    }


    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
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

    @Override
    public void finish() {
        Intent data = new Intent();
        data.putParcelableArrayListExtra(MainActivity.ACCESS_MESSAGE_BOOKING_ACTIVITY, tickets);
        setResult(RESULT_OK, data);
        super.finish();
    }
}
