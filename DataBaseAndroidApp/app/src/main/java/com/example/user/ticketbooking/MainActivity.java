package com.example.user.ticketbooking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    static public ServerApi serverApi;
    static private Retrofit retrofit;
    List<Flight> flights = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setInitData();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        // создаем адаптер
        DataAdapter adapter = new DataAdapter(this, flights);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // получим идентификатор выбранного пункта меню
        int id = item.getItemId();

        // Операции для выбранного пункта меню
        switch (id) {
            case R.id.exit:
                changeUser();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static void connectToServer() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://pure-taiga-64408.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        serverApi = retrofit.create(ServerApi.class);
    }

    private void changeUser() {
        this.finish();
        Intent intent = new Intent(this, Authorization.class);
        startActivity(intent);
    }

    private void setInitData() {
        flights.add(new Flight("Москва", "Казань", "Аэрофлот"));
        flights.add(new Flight("Омск", "Санкт-Петербург", "ТрансАэро"));
        flights.add(new Flight("Москва", "Киров", "Алапаевские Авиалинии"));
        flights.add(new Flight("Москва", "Челябинск", "Челябинский перелет"));
        flights.add(new Flight("Тагил", "Йошкар-Ола", "Тагильский улёт"));
        flights.add(new Flight("Тагил", "Йошкар-Ола", "Тагильский улёт"));
        flights.add(new Flight("Тагил", "Йошкар-Ола", "Тагильский улёт"));
        flights.add(new Flight("Тагил", "Йошкар-Ола", "Тагильский улёт"));

    }
}
