
package com.marcllort.tinder.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("county")
    @Expose
    private String county;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("latitude")
    @Expose
    private double latitude;
    @SerializedName("longitude")
    @Expose
    private double longitude;
    @SerializedName("postalCode")
    @Expose
    private String postalCode;
    @SerializedName("stateProvice")
    @Expose
    private String stateProvice;
    @SerializedName("urlGoogleMaps")
    @Expose
    private String urlGoogleMaps;
    @SerializedName("urlOpenStreetMap")
    @Expose
    private String urlOpenStreetMap;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStateProvice() {
        return stateProvice;
    }

    public void setStateProvice(String stateProvice) {
        this.stateProvice = stateProvice;
    }

    public String getUrlGoogleMaps() {
        return urlGoogleMaps;
    }

    public void setUrlGoogleMaps(String urlGoogleMaps) {
        this.urlGoogleMaps = urlGoogleMaps;
    }

    public String getUrlOpenStreetMap() {
        return urlOpenStreetMap;
    }

    public void setUrlOpenStreetMap(String urlOpenStreetMap) {
        this.urlOpenStreetMap = urlOpenStreetMap;
    }
    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     *
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     * @returns Distance in Meters
     */
    public double distance(  double lat2, double lon2) {

        final int R = 6371; // Radius of the earth
        double lat1 = latitude;
        double lon1 = longitude;
        double el1 = 0;
        double el2 = 0;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

}
