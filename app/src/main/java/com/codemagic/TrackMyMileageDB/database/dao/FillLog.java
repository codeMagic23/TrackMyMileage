package com.codemagic.TrackMyMileageDB.database.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table FILL_LOG.
 */
public class FillLog {

    /** Not-null value. */
    private java.util.Date fillDate;
    private double gallons;
    private Double pricePerGallon;
    private long curMiles;
    /** Not-null value. */
    private String vehicleName;

    public FillLog() {
    }

    public FillLog(java.util.Date fillDate, double gallons, Double pricePerGallon, long curMiles, String vehicleName) {
        this.fillDate = fillDate;
        this.gallons = gallons;
        this.pricePerGallon = pricePerGallon;
        this.curMiles = curMiles;
        this.vehicleName = vehicleName;
    }

    /** Not-null value. */
    public java.util.Date getFillDate() {
        return fillDate;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setFillDate(java.util.Date fillDate) {
        this.fillDate = fillDate;
    }

    public double getGallons() {
        return gallons;
    }

    public void setGallons(double gallons) {
        this.gallons = gallons;
    }

    public Double getPricePerGallon() {
        return pricePerGallon;
    }

    public void setPricePerGallon(Double pricePerGallon) {
        this.pricePerGallon = pricePerGallon;
    }

    public long getCurMiles() {
        return curMiles;
    }

    public void setCurMiles(long curMiles) {
        this.curMiles = curMiles;
    }

    /** Not-null value. */
    public String getVehicleName() {
        return vehicleName;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

}