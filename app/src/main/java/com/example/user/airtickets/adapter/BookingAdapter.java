package com.example.user.airtickets.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.airtickets.R;
import com.example.user.airtickets.activity.BookingActivity;
import com.example.user.airtickets.object.Flight;
import com.example.user.airtickets.object.Ticket;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {
    
    private LayoutInflater inflater;
    private List<Ticket> tickets;
    private Callback callback;

    public BookingAdapter(Context context, List<Ticket> tickets, Callback callback) {
        this.tickets = tickets;
        this.inflater = LayoutInflater.from(context);
        this.callback = callback;
    }
    
    public interface Callback {
        void onButtonClick();
        void onRemoveItem(int position);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public BookingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.booking_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookingAdapter.ViewHolder holder, int position) {
        Ticket ticket = tickets.get(position);
        holder.departureInfoViewCardview.setText(ticket.getPoint_of_departure());
        holder.destinationInfoViewCardView.setText(ticket.getPoint_of_destination());
        holder.companyInfoViewCardView.setText(ticket.getName_of_company());
        holder.inTravelInfoViewCardView.setText(ticket.getTimeInTravel());
        holder.bestPriceInfoViewCardView.setText(ticket.getStrPrice());
        holder.bookButtonCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) {
                    callback.onButtonClick();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView departureInfoViewCardview, destinationInfoViewCardView,
                companyInfoViewCardView, inTravelInfoViewCardView, bestPriceInfoViewCardView;
        final Button bookButtonCardview;

        ViewHolder(View view) {
            super(view);
            bookButtonCardview = (Button) view.findViewById(R.id.cancelBookCardview);
            departureInfoViewCardview = (TextView) view.findViewById(R.id.departure_infoCardview);
            destinationInfoViewCardView = (TextView) view.findViewById(R.id.destination_infoCardview);
            companyInfoViewCardView = (TextView) view.findViewById(R.id.company_infoCardview);
            inTravelInfoViewCardView = (TextView) view.findViewById(R.id.in_travel_infoCardview);
            bestPriceInfoViewCardView = (TextView) view.findViewById(R.id.best_price_infoCardview);
        }
    }
}