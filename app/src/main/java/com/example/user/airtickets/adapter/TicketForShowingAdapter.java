package com.example.user.airtickets.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.airtickets.R;
import com.example.user.airtickets.models.Ticket;

import java.util.List;

public class TicketForShowingAdapter extends RecyclerView.Adapter<TicketForShowingAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Ticket> tickets;

    public TicketForShowingAdapter(Context context, List<Ticket> tickets) {
        inflater = LayoutInflater.from(context);
        this.tickets = tickets;
    }

    @Override
    public TicketForShowingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cardview_info_ticket, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TicketForShowingAdapter.ViewHolder holder, final int position) {
        Ticket ticket = tickets.get(position);
        holder.numberTicketInfoView.setText(String.valueOf(ticket.getIdTicket()));
        holder.classTicketInfoView.setText(ticket.getName());
        holder.priceTicketInfoView.setText(String.valueOf(ticket.getPrice()));
        holder.ticketDescriptionInfoView.setText(ticket.getTicketDescription());
        holder.classDescriptionTicketInfoView.setText(ticket.getClassDescription());
        holder.statusTicketInfoView.setText(ticket.getIsBooked());
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView numberTicketInfoView, classTicketInfoView;
        final TextView priceTicketInfoView, classDescriptionTicketInfoView;
        final TextView ticketDescriptionInfoView, statusTicketInfoView;

        ViewHolder(View view) {
            super(view);
            numberTicketInfoView = (TextView) view.findViewById(R.id.numberTicket_infoCardview);
            classTicketInfoView = (TextView) view.findViewById(R.id.classTicket_infoCardview);
            priceTicketInfoView = (TextView) view.findViewById(R.id.priceTicke_infoCardview);
            classDescriptionTicketInfoView = (TextView) view.findViewById(R.id.classDescription_infoCardview);
            ticketDescriptionInfoView = (TextView) view.findViewById(R.id.ticketDescription_infoCardview);
            statusTicketInfoView = (TextView) view.findViewById(R.id.statusTicket_infoCardview);
        }
    }
}
