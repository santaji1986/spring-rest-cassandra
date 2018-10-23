package com.san.restcassandra.repo;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.san.restcassandra.model.Customer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveCustomerRepository extends ReactiveCrudRepository<Customer, Long> {
	
	@Query("SELECT * FROM customer WHERE firstname = ?0 and lastname  = ?1 ALLOW FILTERING")
	Mono<Customer> findByFirstnameAndLastname(String firstname, String lastname);
	
	Flux<Customer> findByAge(Mono<Integer> age);
}