package com.example.user.airtickets.api.okhttp;

public class EditUserApi extends BaseApi {

    public EditUserApi() {

    }

    public interface Callback {
        void onUserDataUploaded(String request);

        void onFailure(String request);
    }


}
