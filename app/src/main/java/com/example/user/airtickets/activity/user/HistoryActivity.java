package com.example.user.airtickets.activity.user;

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
import com.example.user.airtickets.models.UserData;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private List<Order> orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        createBackButton();
        createRecyclerViewWithOrders(new UserData(UserData.currentLogin, UserData.currentPassword));
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

    private List<Order> getNotBookedOrders(List<Order> orders) {
        List<Order> orderList = new ArrayList<>();
        for (Order order : orders) {
            if (!order.getStatus().equals("booked")) {
                orderList.add(order);
            }
        }
        return orderList;
    }

    private void createRecyclerViewWithOrders(UserData userData) {
        ServerApi serverApi = ServerApi.getInstance();
        ServerApi.DownloadOrdersListener listener = new ServerApi.DownloadOrdersListener() {
            @Override
            public void onDownloadedOrders(List<Order> orders) {
                createRecyclerView(getNotBookedOrders(orders));
                //createRecyclerViewWithNotBookedOrders(getNotBookedOrders(orders));
                //oldOrders = get
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(HistoryActivity.this, message, Toast.LENGTH_LONG).show();
            }
        };
        serverApi.setDownloadOrdersListener(listener);
        serverApi.downloadOrdersFromServer(userData);
    }

    private void createRecyclerView(final List<Order> orders) {
        this.orders = orders;
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listOrders);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        OrderAdapter adapter = new OrderAdapter(this, orders);
        recyclerView.setAdapter(adapter);
    }
}