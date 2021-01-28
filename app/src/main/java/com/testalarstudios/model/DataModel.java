package com.testalarstudios.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataModel implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("lat")
    @Expose
    private double lat;
    @SerializedName("lon")
    @Expose
    private double lon;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
