package com.san.restcassandra.repo;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.san.restcassandra.model.User;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {

}
