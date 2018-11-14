package com.example.user.airtickets.api.okhttp;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BaseApi {

    protected  String BaseUrl = "https://pure-taiga-64408.herokuapp.com";

    public BaseApi() {

    }

    protected interface Listener {
        void onSuccess(JSONObject jsonObject);

        void onSuccess(JSONArray jsonArray);

        void onFailure(String request);
    }

    private Request createRequest(String url) {
        return new Request.Builder()
                .url(url)
                .build();
    }


    protected void sendRequest(String url, final AppCompatActivity appCompatActivity, final Listener listener) {
        OkHttpClient client = new OkHttpClient();
        Request request = createRequest(url);
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
                listener.onFailure(call.request().toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
                appCompatActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject json = new JSONObject(myResponse);
                            Object object = json.get("data");
                            if (object instanceof JSONObject) {
                                listener.onSuccess(json.getJSONObject("data"));
                            } else if (object instanceof JSONArray) {
                                listener.onSuccess(json.getJSONArray("data"));
                            } else {
                                Toast.makeText(appCompatActivity, "Ошибка формата данных с сервера", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            Toast.makeText(appCompatActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
