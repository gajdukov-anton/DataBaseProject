package com.example.user.airtickets.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.airtickets.R;
import com.example.user.airtickets.models.Flight;

import java.util.List;

public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Flight> flights;
    private Callback callback;
    private String buttonText;


    public FlightAdapter(Context context, List<Flight> flights, Callback callback) {
        this.flights = flights;
        this.inflater = LayoutInflater.from(context);
        this.callback = callback;
    }

    public interface Callback {
        void onMoreButtonClick(int flightId, int positionInList);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    @Override
    public FlightAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cardview_flight, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FlightAdapter.ViewHolder holder, final int position) {
        final Flight flight = flights.get(position);
        holder.departureInfoView.setText(flight.getPointOfDeparture());
        holder.destinationInfoView.setText(flight.getPointOfDestination());
        holder.companyInfoView.setText(flight.getCompanyName());
      //  holder.inTravelinfoView.setText(flight.getTimeOfDeparture().substring(0, flight.getTimeOfDeparture().length() - 5));
        holder.inTravelinfoView.setText(flight.getFormattedTimeOfDeparture());
        if (buttonText != null) {
            holder.button.setText(buttonText);
        }
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) {
                    callback.onMoreButtonClick(flight.getIdFlight(), position);
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
                companyInfoView, inTravelinfoView;
        final Button button;

        ViewHolder(View view) {
            super(view);
            button = (Button) view.findViewById(R.id.bookButton);
            departureInfoView = (TextView) view.findViewById(R.id.departure_info);
            destinationInfoView = (TextView) view.findViewById(R.id.destination_info);
            companyInfoView = (TextView) view.findViewById(R.id.company_info);
            inTravelinfoView = (TextView) view.findViewById(R.id.in_travel_info);
        }
    }
}