package com.example.user.airtickets.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.user.airtickets.R;
import com.example.user.airtickets.adapter.BookingAdapter;
import com.example.user.airtickets.object.Ticket;


import java.util.ArrayList;

public class BookingActivity extends AppCompatActivity {

    private ArrayList<Ticket> tickets = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        Bundle bundle = getIntent().getExtras();
        tickets = bundle.getParcelableArrayList("tickets");
        createRecyclerView();
        createBackButton();
    }

    private void createBackButton() {
        ActionBar actionBar =getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void createRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listBooking);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        BookingAdapter adapter = new BookingAdapter(this, tickets, null);
        BookingAdapter.Callback adapterListener = new BookingAdapter.Callback() {
            @Override
            public void onButtonRemoveClick(int position) {
               tickets.remove(position);
               createRecyclerView();
            }

            @Override
            public void onRemoveItem(int position) {
                // Some actions.
            }
        };
        adapter.setCallback(adapterListener);
        recyclerView.setAdapter(adapter);
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
