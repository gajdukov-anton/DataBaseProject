package com.example.user.airtickets.activity.user;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.user.airtickets.R;
import com.example.user.airtickets.adapter.BookedOrderAdapter;
import com.example.user.airtickets.adapter.OrderAdapter;
import com.example.user.airtickets.api.retrofit.ServerApi;
import com.example.user.airtickets.models.Order;
import com.example.user.airtickets.models.ResponseFromServer;
import com.example.user.airtickets.models.UserData;

import java.util.ArrayList;
import java.util.List;

public class AllOrdersActivity extends AppCompatActivity {

    private List<Order> orders;
    private List<Order> oldOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_bookings);
        createBackButton();
        createRecyclerViewWithOrders(new UserData(UserData.currentLogin, UserData.currentPassword));
    }

    private void createRecyclerViewWithOrders(UserData userData) {
        ServerApi serverApi = ServerApi.getInstance();
        ServerApi.DownloadOrdersListener listener = new ServerApi.DownloadOrdersListener() {
            @Override
            public void onDownloadedOrders(List<Order> orders) {
                createRecyclerView(getBookedOrders(orders));
                //createRecyclerViewWithNotBookedOrders(getNotBookedOrders(orders));
                //oldOrders = get
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(AllOrdersActivity.this, message, Toast.LENGTH_LONG).show();
            }
        };
        serverApi.setDownloadOrdersListener(listener);
        serverApi.downloadOrdersFromServer( userData);
    }


    public void startHistoryActivity(View view) {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    private List<Order> getBookedOrders(List<Order> orders) {
        List<Order> orderList = new ArrayList<>();
        for (Order order : orders) {
            if (order.getStatus().equals("booked")) {
                orderList.add(order);
            }
        }
        return orderList;
    }

    private List<Order> getNotBookedOrders(List<Order> orders) {
        List<Order> orderList = new ArrayList<>();
        for (Order order : orders) {
            if (!order.getStatus().equals("booked")) {
                orderList.add(order);
            }
        }
        return orderList;
    }

    private void createRecyclerView(final List<Order> orders) {
        //this.orders = orders;
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listBookedOrders);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        BookedOrderAdapter adapter = new BookedOrderAdapter(this, orders);
        BookedOrderAdapter.Callback adapterListener = new BookedOrderAdapter.Callback() {
            @Override
            public void payOrder(int id) {
                confirmOrder(id);
            }

            @Override
            public void cancelOrder(int id) {
                rejectOrder(id);
            }
        };
        adapter.setCallback(adapterListener);
        recyclerView.setAdapter(adapter);
    }

    private void createRecyclerViewWithNotBookedOrders(final List<Order> orders) {
        //this.orders = orders;
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listOrders);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        OrderAdapter adapter = new OrderAdapter(this, orders);
        recyclerView.setAdapter(adapter);
    }

    private void confirmOrder(int id) {
        ServerApi serverApi = ServerApi.getInstance();
        ServerApi.PostConfirmOrderToServerListener listener = new ServerApi.PostConfirmOrderToServerListener() {
            @Override
            public void onSuccessful(ResponseFromServer responseFromServer) {
                Toast.makeText(AllOrdersActivity.this, responseFromServer.status, Toast.LENGTH_LONG).show();
                createRecyclerViewWithOrders(new UserData(UserData.currentLogin, UserData.currentPassword));
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(AllOrdersActivity.this, message, Toast.LENGTH_LONG).show();
            }
        };
        serverApi.setPostConfirmOrderToServerListener(listener);
        serverApi.postConfirmOrderToServer(id);
    }

    private void rejectOrder(int id) {
        ServerApi serverApi = ServerApi.getInstance();
        ServerApi.PostRejectOrderToServerListener listener = new ServerApi.PostRejectOrderToServerListener() {
            @Override
            public void onSuccessful(ResponseFromServer responseFromServer) {
                Toast.makeText(AllOrdersActivity.this, responseFromServer.status, Toast.LENGTH_LONG).show();
                createRecyclerViewWithOrders(new UserData(UserData.currentLogin, UserData.currentPassword));
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(AllOrdersActivity.this, message, Toast.LENGTH_LONG).show();
            }
        };
        serverApi.setPostRejectOrderToServerListener(listener);
        serverApi.postRejectOrderToServer(id, UserData.currentLogin, UserData.currentPassword);
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

}
