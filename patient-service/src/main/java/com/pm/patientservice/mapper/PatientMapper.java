package com.pm.patientservice.mapper;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.model.Patient;

import java.time.LocalDate;

public class PatientMapper {
    public static PatientResponseDTO toDTO(Patient p) {
        PatientResponseDTO pDTO = new PatientResponseDTO();
        pDTO.setAddress(p.getAddress());
        pDTO.setEmail(p.getEmail());
        pDTO.setName(p.getName());
        pDTO.setDateOfBirth(p.getDateOfBirth().toString());
        return pDTO;
    }

    public static Patient toModel(PatientRequestDTO dto) {
        Patient p = new Patient();
        p.setAddress(dto.getAddress());
        p.setAddress(dto.getAddress());
        p.setEmail(dto.getEmail());
        p.setName(dto.getName());
        p.setDateOfBirth(LocalDate.parse(dto.getDateOfBirth()));
        p.setRegisteredDate(LocalDate.parse(dto.getRegisteredDate()));

        return p;
    }
}
