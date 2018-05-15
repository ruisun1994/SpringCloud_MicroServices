package com.sunrui.locationservice.controller;

import com.sunrui.locationservice.entity.Driver;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DriverController {

    @RequestMapping(value="/drivers", method = RequestMethod.POST)
    public ResponseEntity<Driver> create(@RequestBody)
}
