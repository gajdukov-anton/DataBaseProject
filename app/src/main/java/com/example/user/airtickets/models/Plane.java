package com.example.user.airtickets.models;

public class Plane {
    private int idPlane;
    private String type = "";
    private int idCompany = -1;

    public Plane() {

    }

    public boolean isReadyToUpload() {
        return idCompany != -1 && !type.equals("");
    }

    public void setIdPlane(int idPlane) {
        this.idPlane = idPlane;
    }

    public int getIdPlane() {
        return idPlane;
    }

    public int getIdCompany() {
        return idCompany;
    }

    public String getType() {
        return type;
    }

    public void setIdCompany(int idCompany) {
        this.idCompany = idCompany;
    }

    public void setType(String type) {
        this.type = type;
    }
}
