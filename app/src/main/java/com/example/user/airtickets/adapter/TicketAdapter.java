package com.example.user.airtickets.adapter;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.airtickets.R;
import com.example.user.airtickets.object.Flight;
import com.example.user.airtickets.object.Ticket;

import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Ticket> tickets;
    private TicketAdapter.Callback callback;
    private Flight flight;


    public TicketAdapter(Context context, List<Ticket> tickets, Flight flight) {
        this.tickets = tickets;
        this.inflater = LayoutInflater.from(context);
        this.flight = flight;
    }

    public interface Callback {
        void onButtonClick(int position);

    }

    public void setCallback(TicketAdapter.Callback callback) {
        this.callback = callback;
    }


    @Override
    public TicketAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.ticket_cardview, parent, false); ////!!!!
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TicketAdapter.ViewHolder holder, final int position) {
        Ticket ticket = tickets.get(position);
        ticket.setPointOfDeparture(flight.getPoint_of_departure());
        ticket.setPointOfDestination(flight.getPoint_of_destination());
       // final Ticket ticket = tempTicket;
        holder.numberTicketInfoView.setText(String.valueOf(ticket.getIdTicket()));
        holder.classTicketInfoView.setText(ticket.getClassTicket());
        holder.priceTicketInfoCardview.setText(String.valueOf(ticket.getPrice()));
        holder.bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) {
                    callback.onButtonClick(position); //!!!!!
                }
            }
        });
        tickets.set(position, ticket);
    }


    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView numberTicketInfoView, classTicketInfoView,
                priceTicketInfoCardview;
        final Button bookButton;

        ViewHolder(View view) {
            super(view);
            bookButton = (Button) view.findViewById(R.id.bookTicketButtonCardview);
            numberTicketInfoView = (TextView) view.findViewById(R.id.numberTicket_infoCardview);
            classTicketInfoView = (TextView) view.findViewById(R.id.classTicket_infoCardview);
            priceTicketInfoCardview = (TextView) view.findViewById(R.id.priceTicke_infoCardview);
        }
    }
}
