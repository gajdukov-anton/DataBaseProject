package com.example.user.airtickets.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.user.airtickets.R;
import com.example.user.airtickets.adapter.OrderAdapter;
import com.example.user.airtickets.api.retrofit.ServerApi;
import com.example.user.airtickets.models.Order;
import com.example.user.airtickets.models.ResponseFromServer;
import com.example.user.airtickets.models.User;
import com.example.user.airtickets.models.UserData;

import java.util.ArrayList;
import java.util.List;

public class AllOrdersActivity extends AppCompatActivity {

    private List<Order> orders;

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
                createRecyclerView(orders);
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(AllOrdersActivity.this, message, Toast.LENGTH_LONG).show();
            }
        };
        serverApi.setDownloadOrdersListener(listener);
        serverApi.downloadOrdersFromServer(this, userData);
    }

    private void createRecyclerView(final List<Order> orders) {
        this.orders = orders;
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listOrders);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        OrderAdapter adapter = new OrderAdapter(this, orders);
        OrderAdapter.Callback adapterListener = new OrderAdapter.Callback() {
            @Override
            public void payOrder(int id) {
                //Toast.makeText(AllOrdersActivity.this, "pay" + String.valueOf(orders.size()), Toast.LENGTH_SHORT).show();
                confirmOrder(id);
            }

            @Override
            public void cancelOrder(int id) {
                //Toast.makeText(AllOrdersActivity.this, "cancel", Toast.LENGTH_SHORT).show();
                rejectOrder(id);
            }
        };
        adapter.setCallback(adapterListener);
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
