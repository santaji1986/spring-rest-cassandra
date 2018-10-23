package com.san.restcassandra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.san.restcassandra.model.Customer;
import com.san.restcassandra.model.User;
import com.san.restcassandra.service.CassandraService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class OpenAccessController {
    @Autowired
    private CassandraService cassandraService;

    @RequestMapping(value = "/hello")
    public String hello(@RequestParam(value = "hello", required = false) String hello) {
        return "Response is - " + hello;
    }

    @RequestMapping(value = "/customers")
    public Flux<Customer> getCustomers() {
        return cassandraService.getCustomers();
    }

    @RequestMapping(value = "/users")
    public Flux<User> getUsers() {
        return cassandraService.getUsers();
    }

    @RequestMapping(value = "/customer", method = RequestMethod.POST)
    public Mono<Customer> insertCustomer(@RequestParam Long id, @RequestParam String firstName, @RequestParam String lastName, @RequestParam int age) {

        final Mono<Customer> cus = cassandraService.insertCustomerRecord(id, firstName, lastName, age);

        return cus;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public Mono<User> insertUser(@RequestParam Long id, @RequestParam String username, @RequestParam String password) {

        final Mono<User> user = cassandraService.insertUserRecord(id, username, password);

        return user;
    }

    @RequestMapping(value = "/processExcelFile", method = RequestMethod.POST)
    public void processExcelFile(@RequestParam String filePath) {
        cassandraService.processExcelFile(filePath);
    }

}
