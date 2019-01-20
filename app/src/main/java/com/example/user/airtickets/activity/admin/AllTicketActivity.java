package com.example.user.airtickets.activity.admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.user.airtickets.R;
import com.example.user.airtickets.adapter.TicketForShowingAdapter;
import com.example.user.airtickets.api.retrofit.ServerApi;
import com.example.user.airtickets.models.Ticket;
import com.example.user.airtickets.models.User;
import com.example.user.airtickets.models.UserData;

import java.util.List;

public class AllTicketActivity extends AppCompatActivity {

    private int idFlight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tickets);
        idFlight = getIntent().getIntExtra("idFlight", 0);
        createRecyclerViewWithTickets(idFlight);
    }

    private void createRecyclerViewWithTickets(int idFlight) {
        ServerApi serverApi = ServerApi.getInstance();
        ServerApi.GetAllTicketListener listener = new ServerApi.GetAllTicketListener() {
            @Override
            public void onSuccessful(List<Ticket> tickets) {
                if (tickets.size() > 0) {
                    createRecyclerView(tickets);
                } else {
                    Toast.makeText(AllTicketActivity.this, "Билеты отсутстсвуют", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(AllTicketActivity.this, "Билеты отсутстсвуют", Toast.LENGTH_LONG).show();
            }
        };
        serverApi.setGetAllTicketListener(listener);
        serverApi.getAllTicket(UserData.currentLogin, UserData.currentPassword, idFlight);
    }

    private void createRecyclerView(List<Ticket> tickets) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        TicketForShowingAdapter adapter = new TicketForShowingAdapter(this, tickets);
        recyclerView.setAdapter(adapter);
    }
}
