package com.example.user.airtickets.object;

public class NewUser {

    public  NewUser() {

    }

    private String login;
    private String password;
    private String name;
    private String second_name;
    private String date_of_birth;
    private String sex;

    public void setName(String name) {
        this.name = name;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public String getSex() {
        return sex;
    }
}
