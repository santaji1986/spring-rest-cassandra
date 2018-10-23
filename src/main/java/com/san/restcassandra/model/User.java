package com.san.restcassandra.model;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table("user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @PrimaryKey
    private Long id;
    private String username;
    private String password;
}
