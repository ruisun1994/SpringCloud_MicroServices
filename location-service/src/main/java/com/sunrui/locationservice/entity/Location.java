package com.sunrui.locationservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class Location {

    private double latitude;
    private double longitide;
    private long id;
    private LocalDateTime timestamp;

    public Location() {
    }

    public Location(double latitude, double longitide) {
        this.latitude = latitude;
        this.longitide = longitide;
        this.timestamp = LocalDateTime.now();
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonProperty
    public double getLatitude() {
        return latitude;
    }

    @JsonProperty
    public double getLongitide() {
        return longitide;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitide(double longitide) {
        this.longitide = longitide;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
