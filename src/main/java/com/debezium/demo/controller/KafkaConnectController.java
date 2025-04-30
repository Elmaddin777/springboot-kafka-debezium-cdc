package com.debezium.demo.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/connectors")
public class KafkaConnectController {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String KAFKA_CONNECT_URL = "http://localhost:8083/connectors";

    @PostMapping ("/create")
    public ResponseEntity<String> createConnector() {
        Map<String, Object> connectorConfig = new HashMap<>();
        connectorConfig.put("connector.class", "io.debezium.connector.mysql.MySqlConnector");
        connectorConfig.put("database.hostname", "mysql-d");
        connectorConfig.put("database.port", "3306");
        connectorConfig.put("database.user", "debezium");
        connectorConfig.put("database.password", "dbz");
        connectorConfig.put("topic.prefix", "dbserver1");
        connectorConfig.put("database.server.id", "184054");
        connectorConfig.put("database.include.list", "dataops");
        connectorConfig.put("schema.history.internal.kafka.bootstrap.servers", "kafka:29092");
        connectorConfig.put("schema.history.internal.kafka.topic", "schema-changes.dataops");

        Map<String, Object> body = new HashMap<>();
        body.put("name", "dataops-connector");
        body.put("config", connectorConfig);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(KAFKA_CONNECT_URL, request, String.class);

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

}
