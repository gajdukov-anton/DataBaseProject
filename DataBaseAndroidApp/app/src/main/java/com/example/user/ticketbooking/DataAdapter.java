package com.example.user.ticketbooking;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Flight> flights;

    DataAdapter(Context context, List<Flight> phones) {
        this.flights = phones;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_flight, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, int position) {
        Flight flight = flights.get(position);
        holder.departureInfoView.setText(flight.getPoint_of_departure());
        holder.destinationInfoView.setText(flight.getPoint_of_destination());
        holder.companyInfoView.setText(flight.getName_of_company());
        holder.inTravelinfoView.setText(flight.getTimeInTravel());
        holder.bestPriceInfoView.setText(flight.getStrPrice());
        holder.bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Работает", Toast.LENGTH_SHORT).show();
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