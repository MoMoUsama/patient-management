package com.pm.patientservice.dto;

import com.pm.patientservice.dto.validators.CreatePatientValidationGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// validation to be done by hibernate validator
public class PatientRequestDTO {

    @NotBlank
    @Size(max=100, message = "Name cannot exceed 100 chars")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Use valid Email")
    private String email;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "dateOfBirth is required")
    private String dateOfBirth;

   @NotBlank(groups = CreatePatientValidationGroup.class,
             message = "registeredDate is required")
    private String registeredDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String emil) {
        this.email = emil;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }
}
