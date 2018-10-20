package com.example.user.airtickets.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.airtickets.R;
import com.example.user.airtickets.object.Flight;

import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Flight> flights;
    private Callback callback;


    public TicketAdapter(Context context, List<Flight> flights, Callback callback) {
        this.flights = flights;
        this.inflater = LayoutInflater.from(context);
        this.callback = callback;
    }

    public interface Callback {
        void onButtonClick(int position);
        void onRemoveItem(int position);
    }
    public void setCallback(Callback callback) {
        this.callback = callback;
    }



    @Override
    public TicketAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_flight, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TicketAdapter.ViewHolder holder, final int position) {
        Flight flight = flights.get(position);
        holder.departureInfoView.setText(flight.getPoint_of_departure());
        holder.destinationInfoView.setText(flight.getPoint_of_destination());
        holder.companyInfoView.setText(flight.getName_of_company());
        holder.inTravelinfoView.setText(flight.getTimeInTravel());
        holder.bestPriceInfoView.setText(flight.getStrPrice());
        holder.bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) {
                    callback.onButtonClick(position);
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return flights.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView departureInfoView, destinationInfoView,
            companyInfoView, inTravelinfoView, bestPriceInfoView;
        final Button bookButton;

        ViewHolder(View view) {
            super(view);
            bookButton = (Button) view.findViewById(R.id.bookButton);
            departureInfoView = (TextView) view.findViewById(R.id.departure_info);
            destinationInfoView = (TextView) view.findViewById(R.id.destination_info);
            companyInfoView = (TextView) view.findViewById(R.id.company_info);
            inTravelinfoView = (TextView) view.findViewById(R.id.in_travel_info);
            bestPriceInfoView = (TextView) view.findViewById(R.id.best_price_info);
        }
    }
}