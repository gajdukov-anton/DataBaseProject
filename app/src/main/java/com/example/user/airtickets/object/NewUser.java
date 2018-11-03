package com.example.user.airtickets.object;

public class NewUser {

    public NewUser() {

    }

    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String sex;
    private String address = "Paris";
    private String status = "user";

    public boolean isReadyToUpload() {
        if (login == null || password == null ||
                firstName == null || lastName == null ||
                dateOfBirth == null || sex == null ||
                address == null || status == null) {
            return false;
        }
        if (login.equals("") || password.equals("") ||
                firstName.equals("") || lastName.equals("") ||
                dateOfBirth.equals("") || sex.equals("") ||
                address.equals("") || status.equals("")) {
            return false;
        }
        return true;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDateOfBirth(String date_of_birth) {
        this.dateOfBirth = date_of_birth;
    }

    public void setSecondName(String second_name) {
        this.lastName = second_name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return lastName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getSex() {
        return sex;
    }

    public String getAddress() {
        return address;
    }

    public String getStatus() {
        return status;
    }
}
