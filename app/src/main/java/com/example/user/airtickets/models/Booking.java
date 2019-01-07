package com.example.user.airtickets.models;

import java.util.List;

public class Booking {
    private List<Ticket> tickets;
    private String login;
    private String password;
    private String card = "551541728541784";

    public Booking() {

    }


    public void setCard(String card) {
        this.card = card;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public String getCard() {
        return card;
    }
}
