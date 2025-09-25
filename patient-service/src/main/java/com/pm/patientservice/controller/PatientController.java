package com.pm.patientservice.controller;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.dto.validators.CreatePatientValidationGroup;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patient", description = "API for Managing patients") //for documentation
public class   PatientController {

    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);
   PatientService patientService;
   public PatientController(PatientService patientService) {
       this.patientService = patientService;
   }

   @GetMapping
   @Operation( summary = "Get Patients")
   public ResponseEntity<List<PatientResponseDTO>> getPatients() {
       List<PatientResponseDTO> patients = patientService.getPatients();
       return ResponseEntity.ok().body(patients);
   }

    @PostMapping
    @Operation( summary = "Create new Patient")
    public ResponseEntity<PatientResponseDTO> createPatient(
            @Validated({Default.class, CreatePatientValidationGroup.class}) @RequestBody PatientRequestDTO PRDTO
    ) {
        logger.info("Controller: In the createPatient()");
        PatientResponseDTO respDTO = patientService.createPatient(PRDTO);
        return ResponseEntity.ok().body(respDTO);
    }

    // localhost:4000/patients/2324-4545-54
    @PutMapping("/{id}")
    @Operation( summary = "Update Patient")
    public ResponseEntity<PatientResponseDTO> updatePatient(
            @Valid @RequestBody PatientRequestDTO PRDTO,
            @PathVariable String  id
    )
    {
        PatientResponseDTO respDTO = patientService.updatePatient(id, PRDTO);
        return ResponseEntity.ok().body(respDTO);
    }

    @DeleteMapping("/{id}")
    @Operation( summary = "Delete Patient")
    public ResponseEntity<PatientResponseDTO> deletePatient (@PathVariable String  id)
    {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build(); // status code 204
    }

}
