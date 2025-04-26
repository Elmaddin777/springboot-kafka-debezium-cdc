package com.debezium.demo.messaging;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CDCEventListener {

    @KafkaListener(topics = "dbserver1.dataops.example", groupId = "cdc-demo-group")
    public void listen(String message) {
        System.out.println("Received CDC event: " + message);
    }

}
