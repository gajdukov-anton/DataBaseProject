package com.example.user.airtickets.models;

import java.util.List;

public class UserData {
    public String login;
    public String password;
    public static String currentLogin;
    public static String currentPassword;
    public static int currentIdUser;
    public static List<Ticket> bookedTickets;

    public UserData(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }
}
