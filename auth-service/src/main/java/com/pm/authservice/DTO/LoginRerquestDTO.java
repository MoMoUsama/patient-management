package com.pm.authservice.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRerquestDTO {

    @NotBlank( message = "An Email Is Required")
    @Email( message = "Valid Email is required")
    private String email;

    @NotBlank( message = "Password Is Required")
    @Size(min=8, message = "Password Should be at least 8 characters Long")
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
