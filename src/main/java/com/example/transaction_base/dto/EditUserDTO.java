package com.example.transaction_base.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(hidden = true)
public class EditUserDTO {

    private String firstName;
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
