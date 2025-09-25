package com.pm.patientservice.repository;

import com.pm.patientservice.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
// <entityToManage, typeOfThePK>
public interface PatientRepository extends JpaRepository<Patient, String > {
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, String  id);
}
