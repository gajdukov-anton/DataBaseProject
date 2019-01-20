package com.example.user.airtickets.models;

import com.example.user.airtickets.api.retrofit.ServerApi;

import java.util.ArrayList;
import java.util.List;

public class AdminData {
    private List<Class> classList = new ArrayList<>();
    private List<Airport> airportList = new ArrayList<>();
    private List<Company> companyList = new ArrayList<>();
    private List<Plane> planeList = new ArrayList<>();

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

    public String getCompanyNameByPlane(Plane plane) {
        for (Company company : companyList) {
            if (company.getIdCompany() == plane.getIdCompany()) {
                return company.getName();
            }
        }
        return "";
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

    public boolean isAirportExist(Airport airport) {
        for (Airport currentAirport : airportList) {
            if (currentAirport.getLocation().toLowerCase().equals(airport.getLocation().toLowerCase())
                    && currentAirport.getName().toLowerCase().equals(airport.getName().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean isClassExist(Class newClass) {
        for (Class currentClass : classList) {
            if (currentClass.getDescription().toLowerCase().equals(newClass.getDescription().toLowerCase())
                    && currentClass.getName().toLowerCase().equals(newClass.getName().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean isCompanyExist(Company company) {
        for (Company currentCompany : companyList) {
            if (currentCompany.getName().toLowerCase().equals(company.getName().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean isPlaneExist(Plane plane) {
        for (Plane currentPlane : planeList) {
            if (currentPlane.getType().toLowerCase().equals(plane.getType().toLowerCase()) &&
                    currentPlane.getIdCompany() == plane.getIdCompany()) {
                return true;
            }
        }
        return false;
    }
}
