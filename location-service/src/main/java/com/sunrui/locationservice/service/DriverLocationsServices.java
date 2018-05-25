package com.sunrui.locationservice.service;

import com.sunrui.locationservice.entity.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DriverLocationsServices {
    private String driverId;

    //key: location id
    //value: location
    private HashMap<Long, Location> locations;
    private long curLocationId;

    public DriverLocationsServices(String driverId) {
        this.driverId = driverId;
        this.locations = new HashMap<>();
        this.curLocationId = 0;
    }

    public void addLocation(Location location) {
        long id = ++curLocationId;
        location.setId(id);
        locations.put(id, location);
    }

    public List<Location> getAll() {
        return new ArrayList<>(locations.values());
    }

    public Location getLastLocation() {
        return locations.get(curLocationId);
    }

    public Location getLocation(long locationId) {
        if(!locations.containsKey(locationId)) {
            return null;
        }
        return locations.get(locationId);
    }

    public boolean updateLocation(long locationId, Location newLocation) {
        if(!locations.containsKey(locationId)) {
            return false;
        }
        Location location = locations.get(locationId);
        location.setLatitude(newLocation.getLatitude());
        location.setLongitude(newLocation.getLongitude());
        return true;
    }

    public boolean deleteLocation(long locationId) {
        if(!locations.containsKey(locationId)) {
            return false;
        }
        locations.remove(locationId);
        return true;
    }
}
