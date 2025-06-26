package com.fitnesapp.demo.services;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class NodeAuthClientService {
    private final String nodeAuthServiceUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    public NodeAuthClientService() {
        this.nodeAuthServiceUrl = System.getProperty("NODE_JS_AUTH_SERVICE_URL");
        if (this.nodeAuthServiceUrl == null) {
            throw new IllegalStateException("NODE_JS_AUTH_SERVICE_URL environment variable is not set!");
        }
    }

    public boolean validateToken(String token) {
        String url = nodeAuthServiceUrl + "/validate";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> body = new HashMap<>();
        body.put("token", token);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception e) {
            return false;
        }
    }

    public ResponseEntity<String> getUserInfo(String token) {
        String url = nodeAuthServiceUrl + "/userinfo";
        HttpHeaders headers = new HttpHeaders();
        token = token.trim();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Void> request = new HttpEntity<>(headers);
        try {
            return restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
} 