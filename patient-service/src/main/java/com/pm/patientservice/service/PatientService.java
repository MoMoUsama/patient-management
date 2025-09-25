package com.pm.patientservice.service;

import billing.BillingServiceGrpc;
import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.exception.EmailAlreadyExistsException;
import com.pm.patientservice.exception.PatientNotExistsException;
import com.pm.patientservice.grpc.BillingServiceGrpcClient;
import com.pm.patientservice.kafka.KafkaProducer;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;

@Service
public class PatientService {

    private static final Logger logger = LoggerFactory.getLogger(PatientService.class);
    private PatientRepository patientRepository;
    private BillingServiceGrpcClient  billingServiceGrpcClient;
    private KafkaProducer kafkaProducer;


    public PatientService(PatientRepository patientRepository,
                          BillingServiceGrpcClient billingServiceGrpcClient,
                          KafkaProducer kafkaProducer) {
        this.patientRepository = patientRepository;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
        this.kafkaProducer = kafkaProducer;
    }

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();
        logger.info("Service: The Retrrieved IDs from the DB:");
        for(Patient p: patients) {
            logger.info("Service: ID = {}", p.getId());
        }
        return patients.stream()
                .map( patient -> PatientMapper.toDTO(patient)).toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO PR) {
        if(patientRepository.existsByEmail(PR.getEmail())) {
            throw new EmailAlreadyExistsException("This Email: " +PR.getEmail()+" is already used by another patient");
        }
        Patient patient = PatientMapper.toModel(PR);
        patient.setId(UUID.randomUUID().toString());
        if (!patient.getId().matches("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}")) {
            throw new IllegalStateException("Invalid UUID format: " + patient.getId());
        }
        logger.info("Service: generated ID = {}", patient.getId());
        Patient savedPatient = patientRepository.save(patient);

        // gRPC call
        billingServiceGrpcClient.createBillingAccount(savedPatient.getId(), savedPatient.getName(), savedPatient.getEmail());

        //kafka call
        kafkaProducer.sendEvent(savedPatient);

        return  PatientMapper.toDTO(savedPatient);
    }

    public PatientResponseDTO updatePatient(String  id, PatientRequestDTO PR) {
        Patient patient = patientRepository.findById(id).orElseThrow( () -> new PatientNotExistsException("This ID: " +id+" is not associated with any patient")) ;
        if(patientRepository.existsByEmailAndIdNot(PR.getEmail(), id)) {
            throw new EmailAlreadyExistsException("This Email: " +PR.getEmail()+" is already used by another patient");
        }

        // updating the patient information
        patient.setName(PR.getName());
        patient.setEmail(PR.getEmail());
        patient.setDateOfBirth(LocalDate.parse(PR.getDateOfBirth()));
        patient.setAddress(PR.getAddress());
        Patient updatedPatient = patientRepository.save(patient);

        return  PatientMapper.toDTO(updatedPatient);
    }

    public void deletePatient(String  id) {
        patientRepository.deleteById(id);
    }
}
