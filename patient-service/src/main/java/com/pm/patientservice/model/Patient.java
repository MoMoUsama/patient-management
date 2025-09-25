package com.pm.patientservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Patient {

    @Id
    //@GeneratedValue(strategy = GenerationType.UUID) // Explicitly use UUID strategy
    //@Column(columnDefinition = "VARCHAR(36)")
    private String id;

    @NotNull
    @Column( name = "name")
    private String name;

    @NotNull
    @Column( name = "address")
    private String address;

    @NotNull
    @Column( name = "email", unique = true)
    private String email;

    @NotNull
    @Column( name = "dateOfBirth")
    private LocalDate dateOfBirth; //saved as string in DB

    @NotNull
    @Column( name = "registeredDate")
    private LocalDate registeredDate;


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public LocalDate getRegisteredDate() {
        return registeredDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setRegisteredDate(LocalDate registeredDate) {
        this.registeredDate = registeredDate;
    }
}
