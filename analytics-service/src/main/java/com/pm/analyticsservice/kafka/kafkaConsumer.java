package com.pm.analyticsservice.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Service
public class kafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(kafkaConsumer.class);

    // groupId tell kafka Broaker who this consumer is
    @KafkaListener(topics = "patient", groupId = "analytics-service") //subscribe to a topic
    public void conumeEvent(byte [] event){
        try {
            PatientEvent patientEvent = PatientEvent.parseFrom(event);
            // .... perform business logic here

            log.info("Patient Event received: [PatientID={}, PatientName={}, PatientEmail{}]"
                    + patientEvent.getPatientId(),
                    patientEvent.getName(),
                    patientEvent.getEmail());

        } catch (InvalidProtocolBufferException e) {
            log.error("ERROR Deserializing an event {}", e.getMessage());
        }
    }
}
