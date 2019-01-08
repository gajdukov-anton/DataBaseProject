package com.example.user.airtickets.models;

public class Class {
    private String description;
    private String name;
    private int idClass;

    public Class() {

    }

    public void setIdClass(int idClass) {
        this.idClass = idClass;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdClass() {
        return idClass;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
