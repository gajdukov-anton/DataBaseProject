package com.example.user.airtickets.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.airtickets.R;
import com.example.user.airtickets.models.Order;

import java.util.List;

public class BookedOrderAdapter extends RecyclerView.Adapter<BookedOrderAdapter.ViewHolder>{
    private LayoutInflater inflater;
    private List<Order> orders;
    private Callback callback;
    private String buttonText;

    public BookedOrderAdapter(Context context, List<Order> orders) {
        this.orders = orders;
        this.inflater = LayoutInflater.from(context);
    }

    public interface Callback {
        void payOrder(int id);

        void cancelOrder(int id);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public BookedOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cardview_booked_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookedOrderAdapter.ViewHolder holder, final int position) {
        final Order order = orders.get(position);
        holder.cardNumberInfoView.setText(String.valueOf(order.getCardNumber()));
        holder.priceInfoCardview.setText(String.valueOf(order.getBookingPrice()));
        holder.dateInfoView.setText(order.getDate());
        holder.statusInfoView.setText(order.getStatus());
        // if (order.getStatus().equals("booked")) {
        holder.attemptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) {
                    callback.payOrder(order.getIdBooking());
                }
            }
        });
        holder.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) {
                    callback.cancelOrder(order.getIdBooking());
                }
            }
        });
        //} else {
        if (holder.statusInfoView.getText().toString().equals("booked")) {
            holder.attemptButton.setVisibility(View.VISIBLE);
            holder.cancelButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView priceInfoCardview, statusInfoView,
                cardNumberInfoView, dateInfoView;
        final Button attemptButton, cancelButton;

        ViewHolder(View view) {
            super(view);
            priceInfoCardview = (TextView) view.findViewById(R.id.price_infoCardview);
            statusInfoView = (TextView) view.findViewById(R.id.status_infoCardview);
            cardNumberInfoView = (TextView) view.findViewById(R.id.cardNumber_infoCardview);
            dateInfoView = (TextView) view.findViewById(R.id.date_infoCardview);
            attemptButton = (Button) view.findViewById(R.id.attemptOrderCardview);
            cancelButton = (Button) view.findViewById(R.id.cancelOrderCardview);
        }
    }
}
