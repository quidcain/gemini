package com.gemini.beans.json;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 6/20/18
 * Time: 8:52 AM
 */
public class CoordinatesResponse {

    private String countyCode;
    private double latitude;
    private double longitude;

    public CoordinatesResponse(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}