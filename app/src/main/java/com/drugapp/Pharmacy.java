package com.drugapp;

/**
 * Created by IRFAN on 3/29/2017.
 */

public class Pharmacy {
    String name,location,lat,longitude;

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLat() {
        return lat;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
