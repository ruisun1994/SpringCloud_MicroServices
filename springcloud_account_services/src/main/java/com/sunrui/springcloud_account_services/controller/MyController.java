package com.sunrui.springcloud_account_services.controller;

import com.sunrui.springcloud_account_services.entity.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class MyController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/sayHello", method = RequestMethod.GET)
    public Model model(@RequestParam(value = "name", defaultValue = "world") String name) {
        return new Model(counter.incrementAndGet(),
                String.format(template, name));
    }
}
