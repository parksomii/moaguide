package com.moaguide.refactor.common.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.TreeMap;

@RestController
public class HealthCheckController {

    @Value("${server.env}")
    private String env;



    @GetMapping("/hc")
    public ResponseEntity<?> healthCheck() {
        Map<String,String> responseData = new TreeMap<>();
        responseData.put("env",env);
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/env")
    public ResponseEntity<?> getenv() {
        return ResponseEntity.ok(env);
    }
}
