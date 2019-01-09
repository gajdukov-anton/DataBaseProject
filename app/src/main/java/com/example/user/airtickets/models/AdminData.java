package com.example.user.airtickets.models;

import com.example.user.airtickets.api.retrofit.ServerApi;

import java.util.ArrayList;
import java.util.List;

public class AdminData {
    private List<Class> classList ;
    private List<Airport> airportList;
    private List<Company> companyList;
    private List<Plane> planeList;

    private static class AdminDataHolder {
        private final static AdminData instance = new AdminData();
    }

    public static AdminData getInstance() {
        return AdminDataHolder.instance;
    }

    private AdminData() {
        downloadClassesFromServer();
        downloadAirportsFromServer();
        downloadCompaniesFromServer();
        downloadPlanesFromServer();
    }

    public List<Airport> getAirportList() {
        return airportList;
    }

    public List<Class> getClassList() {
        return classList;
    }

    public List<Company> getCompanyList() {
        return companyList;
    }

    public List<Plane> getPlaneList() {
        return planeList;
    }

    private void downloadClassesFromServer() {
        ServerApi serverApi = ServerApi.getInstance();
        ServerApi.GetAllClassesListener listener = new ServerApi.GetAllClassesListener() {
            @Override
            public void onSuccessful(List<Class> classes) {
                classList = classes;
            }

            @Override
            public void onFailure(String message) {
                classList = new ArrayList<>();
            }
        };
        serverApi.setGetAllClassesListener(listener);
        serverApi.getAllClasses();
    }

    private void downloadAirportsFromServer() {
        ServerApi serverApi = ServerApi.getInstance();
        ServerApi.GetAllAirportsListener listener = new ServerApi.GetAllAirportsListener() {
            @Override
            public void onSuccessful(List<Airport> airports) {
                airportList = airports;
            }

            @Override
            public void onFailure(String message) {
                airportList = new ArrayList<>();
            }
        };
        serverApi.setGetAllAirportsListener(listener);
        serverApi.getAllAirports();
    }

    private void downloadCompaniesFromServer() {
        ServerApi serverApi = ServerApi.getInstance();
        ServerApi.GetAllCompaniesListener listener = new ServerApi.GetAllCompaniesListener() {
            @Override
            public void onSuccessful(List<Company> companies) {
                companyList = companies;
            }

            @Override
            public void onFailure(String message) {
                companyList = new ArrayList<>();
            }
        };
        serverApi.setGetAllCompaniesListener(listener);
        serverApi.getAllCompanies();
    }

    private void downloadPlanesFromServer() {
        ServerApi serverApi = ServerApi.getInstance();
        ServerApi.GetAllPlanesListener listener = new ServerApi.GetAllPlanesListener() {
            @Override
            public void onSuccessful(List<Plane> planes) {
                planeList = planes;
            }

            @Override
            public void onFailure(String message) {
                planeList = new ArrayList<>();
            }
        };
        serverApi.setGetAllPlanesListener(listener);
        serverApi.getAllPlanes();
    }

}
