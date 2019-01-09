package com.example.user.airtickets.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String login;
    private int idUser;
    private String password;
    private String firstName;
    private String lastName;
    private String dateOfBirth = "2012-12-12";
    private String sex;
    private String address = "Москва";
    private String status = "user";
    private String urlImage = "";
    private String newPassword = "123";

    public User() {

    }

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

    public boolean isReadyToUdate() {
        if (login == null || firstName == null || lastName == null ||
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        Bundle bundle = new Bundle();
        bundle.putString("login", login);
        bundle.putString("password", password);
        bundle.putString("firstName", firstName);
        bundle.putString("lastName", lastName);
        bundle.putString("dateOfBirth", dateOfBirth);
        bundle.putString("sex", sex);
        bundle.putString("address", address);
        bundle.putString("status", status);
        bundle.putString("urlImage", urlImage);
        parcel.writeBundle(bundle);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public User(Parcel parcel) {
        Bundle bundle = parcel.readBundle();
        this.login = bundle.getString("login");
        this.password = bundle.getString("password");
        this.firstName = bundle.getString("firstName");
        this.lastName = bundle.getString("lastName");
        this.dateOfBirth = bundle.getString("dateOfBirth");
        this.sex = bundle.getString("sex");
        this.address = bundle.getString("address");
        this.status = bundle.getString("status");
        this.urlImage = bundle.getString("urlImage");
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
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

    public void setLastName(String second_name) {
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

    public String getLastName() {
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

    public String getUrlImage() {
        return urlImage;
    }

    public int getIdUser() {
        return idUser;
    }

    public void editFirstName(String value) {
        if (!value.equals("")) {
            this.firstName = value;
        }
    }

    public void editLastName(String value) {
        if (!value.equals("")) {
            this.lastName = value;
        }
    }

    public void editLogin(String value) {
        if (!value.equals("")) {
            this.login = value;
        }
    }

    public void editDateOfBirth(String value) {
        this.dateOfBirth = this.dateOfBirth.replace('T', ' ');
        this.dateOfBirth = this.dateOfBirth.replace('Z', ' ');
        if (!value.equals("")) {
           // this.dateOfBirth = value;
        }
    }

    public void editSex(String value) {
        if (!value.equals("")) {
            this.sex = value;
        }
    }

    public void editNewPassword(String value) {
        if (!value.equals("")) {
            this.newPassword = value;
        }
    }



    public String getNewPassword() {
        return newPassword;
    }
}
