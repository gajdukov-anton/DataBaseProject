package com.example.user.airtickets.object;

import java.util.List;

public class Booking {
    private List<Ticket> tickets;
    private String login;
    private String password;
    private String numberCard = "551541728541784";

    public Booking() {

    }


    public void setNumberCard(String numberCard) {
        this.numberCard = numberCard;
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

    public String getNumberCard() {
        return numberCard;
    }
}
