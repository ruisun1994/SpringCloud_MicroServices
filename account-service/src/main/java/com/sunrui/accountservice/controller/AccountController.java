package com.sunrui.accountservice.controller;
import com.sunrui.accountservice.entity.Account;
import com.sunrui.accountservice.service.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.misc.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController

public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(value = "/accounts", method = RequestMethod.POST)
    public ResponseEntity<Account> create(
            @RequestBody(required = false) Account account) {
        accountRepository.save(account);

        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/accounts", method = RequestMethod.GET)
    public ResponseEntity<List<Account>> getAll() {
        List<Account> accounts = new ArrayList<>();
        accountRepository.findAll().iterator().forEachRemaining(accounts::add);

        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @RequestMapping(value = "/accounts/{id}", method = RequestMethod.GET)
    public ResponseEntity<Account> getAccountId(@PathVariable("id") String id) {
        Account account = null;
        Optional<Account> optional = accountRepository.findById(Long.parseLong(id));

        if(!optional.isPresent()) {
            return new ResponseEntity<>(account, HttpStatus.NOT_FOUND);
        }

        account = optional.get();

        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @RequestMapping(value = "/accounts/find", method = RequestMethod.GET)
    public ResponseEntity<List<Account>> findByName(
            @RequestParam(value = "firstName", defaultValue = "") String firstName,
            @RequestParam(value = "lastName", defaultValue = "") String lastName) {

        List<Account> accounts = new ArrayList<>();

        if(firstName.equals("")) {
            accountRepository.findByLastName(lastName).iterator().forEachRemaining(accounts::add);
        } else if(lastName.equals("")) {
            accountRepository.findByFirstName(firstName).iterator().forEachRemaining(accounts::add);
        } else {
            accountRepository.findByFirstNameAndLastName(firstName, lastName).iterator().forEachRemaining(accounts::add);
        }

        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }
}
