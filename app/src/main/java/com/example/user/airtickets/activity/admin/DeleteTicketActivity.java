package com.example.user.airtickets.activity.admin;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.user.airtickets.R;
import com.example.user.airtickets.adapter.TicketForDeleteAdapter;
import com.example.user.airtickets.api.retrofit.ServerApi;
import com.example.user.airtickets.models.ResponseFromServer;
import com.example.user.airtickets.models.Ticket;

import java.util.ArrayList;
import java.util.List;

public class DeleteTicketActivity extends AppCompatActivity {

    private int idFlight;
    private List<Ticket> tickets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_ticket);
        idFlight = getIntent().getIntExtra("idFlight", -1);
        createBackButton();
        createRecyclerViewWithTickets(idFlight);
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

    private void createRecyclerViewWithTickets(int idFlight) {
        ServerApi serverApi = ServerApi.getInstance();
        ServerApi.DownloadTicketsListener listener = new ServerApi.DownloadTicketsListener() {
            @Override
            public void onDownloadedTickets(List<Ticket> tickets) {
                createRecyclerView(tickets);
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(DeleteTicketActivity.this, "Билеты отсутсвуют", Toast.LENGTH_SHORT).show();
                createRecyclerView(new ArrayList<Ticket>());
            }
        };
        serverApi.setDownloadTicketsListener(listener);
        serverApi.downloadTickets(this, idFlight);
    }

    private void createRecyclerView(final List<Ticket> tickets) {
        this.tickets = tickets;
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listTicket);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        TicketForDeleteAdapter adapter = new TicketForDeleteAdapter(this, this.tickets);
        TicketForDeleteAdapter.Callback adapterListener = new TicketForDeleteAdapter.Callback() {
            @Override
            public void onButtonClick(int position) {
                deleteTicket(tickets.get(position).getIdTicket());
                // createDialogFragment(position);
                //checkStatusTicket(tickets.get(position).getIdTicket(), position);
            }
        };
        adapter.setCallback(adapterListener);
        recyclerView.setAdapter(adapter);
    }

    private void deleteTicket(final int idTicket) {
        ServerApi serverApi = ServerApi.getInstance();
        ServerApi.DeleteTicketListener listener = new ServerApi.DeleteTicketListener() {
            @Override
            public void onSuccessful(ResponseFromServer responseFromServer) {
                Toast.makeText(DeleteTicketActivity.this, responseFromServer.status + String.valueOf(idTicket), Toast.LENGTH_SHORT).show();
                createRecyclerViewWithTickets(idFlight);
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(DeleteTicketActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        };
        serverApi.setDeleteTicketListener(listener);
        serverApi.deleteTicket(idTicket);
    }
}
