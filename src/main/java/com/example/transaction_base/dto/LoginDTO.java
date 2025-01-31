package com.example.transaction_base.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(hidden = true)
public class LoginDTO {

    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
