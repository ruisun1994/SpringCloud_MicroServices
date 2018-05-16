package com.sunrui.locationservice.controller;

import com.sunrui.locationservice.entity.Location;
import com.sunrui.locationservice.service.DriverLocationsServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class LocationServiceController {

    private final AtomicLong counter = new AtomicLong();
    private Random random = new Random();

    //key: driver id
    //value: a list of driver's locations

    private HashMap<String, DriverLocationsServices> locationsHashMap = new HashMap<>();
    @RequestMapping(value = "/drivers/{id}/locations", method = RequestMethod.POST)
    public ResponseEntity<Location> createLocation(
            @PathVariable("id") String id,
            @RequestBody(required = false) Location inputLocation) {
        Location location;

        if(inputLocation == null) {
            location = new Location(
                    random.nextInt(90),
                    random.nextInt(90)
            );
        }else {
            location = new Location(
                    inputLocation.getLatitude(),
                    inputLocation.getLongitude()
            );
        }

        if(!locationsHashMap.containsKey(id)) {
            locationsHashMap.put(id, new DriverLocationsServices(id));
        }

        locationsHashMap.get(id).addLocation(location);
        return new ResponseEntity<>(location, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/drivers/{id}/locations", method = RequestMethod.GET)
    public ResponseEntity<List<Location>> getAll(
            @PathVariable("id") String id) {
        //validate if driver exists
        if (!DriverController.isDriverValid(id)) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }

        DriverLocationsServices driverLocationsServices = locationsHashMap.get(id);
        if (driverLocationsServices == null) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
        return new ResponseEntity<>(driverLocationsServices.getAll(), HttpStatus.OK);
    }


    @RequestMapping(value = "/drivers/{id}/locations/{locationId}", method = RequestMethod.GET)
    public ResponseEntity<Location> getLocation(
            @PathVariable("id") String id,
            @PathVariable("locationId") String locationId) {
        Location location = null; //?

        //validate if driver id exist
        if (!locationsHashMap.containsKey(id)) {
            return new ResponseEntity<>(location, HttpStatus.BAD_REQUEST);
        }

        DriverLocationsServices driverLocationsServices = locationsHashMap.get(id);

        location = driverLocationsServices.getLocation(Long.parseLong(locationId));

        //validate if location exist
        if (location == null) {
            return new ResponseEntity<>(location, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(location, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/drivers/{id}/locations/current", method = RequestMethod.GET)
    public ResponseEntity<Location> getCurrentLocation(@PathVariable("id") String id) {
        Location location = null;
        if (!locationsHashMap.containsKey(id)) {
            return new ResponseEntity<>(location, HttpStatus.BAD_REQUEST);
        }
        DriverLocationsServices driverLocationsServices = locationsHashMap.get(id);
        return new ResponseEntity<>(driverLocationsServices.getLastLocation(), HttpStatus.OK);
    }

    @RequestMapping(value = "/drivers/{id}/locations/{locationId}", method = RequestMethod.PUT)
    public ResponseEntity<Location> update(
            @RequestBody Location location,
            @PathVariable("id") String id,
            @PathVariable("locationId") String locationId) {
        Location tempLocation = null;
        if (!locationsHashMap.containsKey(id)) {
            return new ResponseEntity<>(tempLocation, HttpStatus.BAD_REQUEST);
        }
        DriverLocationsServices driverLocationsServices = locationsHashMap.get(id);

        if (driverLocationsServices.updateLocation(Long.parseLong(locationId), location)) {
            return new ResponseEntity<>(driverLocationsServices.getLocation(Long.parseLong(locationId)), HttpStatus.OK);
        } else {
           return new ResponseEntity<>(tempLocation, HttpStatus.BAD_REQUEST);
        }
    }

    private ResponseEntity<Location> deleteImpl(String id, String locationId) {
        Location tempLocation = null;
        if (!locationsHashMap.containsKey(id)) {
            return new ResponseEntity<>(tempLocation, HttpStatus.BAD_REQUEST);
        }
        DriverLocationsServices driverLocationsServices = locationsHashMap.get(id);

        if(driverLocationsServices.deleteLocation(Long.parseLong(locationId))) {
            return new ResponseEntity<>(tempLocation, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(tempLocation, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/drivers/{id}/locations/{locationId}", method = RequestMethod.DELETE)
    public ResponseEntity<Location> delete(
            @PathVariable("id") String id,
            @PathVariable("locationId") String locationId) {
        return this.deleteImpl(id, locationId);
    }

    @RequestMapping(value = "/drivers/{id}/locations/{locationId}/delete", method = RequestMethod.POST)
    public ResponseEntity<Location> deleteByPost(
            @PathVariable("id") String id,
            @PathVariable("locationId") String locationId) {
        return this.deleteImpl(id, locationId);
    }




}
