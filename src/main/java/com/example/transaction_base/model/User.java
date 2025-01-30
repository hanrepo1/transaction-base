package com.example.transaction_base.model;

import lombok.Data;

@Data
public class User {

    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String profileImage;
    private Long balance;

    public User(Long id, String email, String password, String firstName, String lastName, String profileImage, Long balance) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profileImage = profileImage;
        this.balance = balance;
    }

    public User(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

}