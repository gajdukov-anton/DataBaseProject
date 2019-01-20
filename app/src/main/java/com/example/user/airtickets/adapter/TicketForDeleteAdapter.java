package com.example.user.airtickets.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.airtickets.R;
import com.example.user.airtickets.models.Ticket;

import java.util.List;

public class TicketForDeleteAdapter extends RecyclerView.Adapter<TicketForDeleteAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Ticket> tickets;
    private Callback callback;

    public TicketForDeleteAdapter(Context context, List<Ticket> tickets) {
        this.tickets = tickets;
        inflater = LayoutInflater.from(context);
    }

    public interface Callback {
        void onButtonClick(int position);

    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public TicketForDeleteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cardview_ticket, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TicketForDeleteAdapter.ViewHolder holder, final int position) {
        Ticket ticket = tickets.get(position);
        holder.numberTicketInfoView.setText(String.valueOf(ticket.getIdTicket()));
        holder.classTicketInfoView.setText(ticket.getName());
        holder.priceTicketInfoCardView.setText(String.valueOf(ticket.getPrice()));
        holder.deleteButton.setText("Удалить");
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) {
                    callback.onButtonClick(position);
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
        final TextView numberTicketInfoView, classTicketInfoView, priceTicketInfoCardView;
        final Button deleteButton;

        ViewHolder(View view) {
            super(view);
            deleteButton = (Button) view.findViewById(R.id.bookTicketButtonCardview);
            numberTicketInfoView = (TextView) view.findViewById(R.id.numberTicket_infoCardview);
            classTicketInfoView = (TextView) view.findViewById(R.id.classTicket_infoCardview);
            priceTicketInfoCardView = (TextView) view.findViewById(R.id.priceTicke_infoCardview);
        }
    }
}
