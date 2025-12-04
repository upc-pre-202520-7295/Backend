package com.betalyze.dataretrieval.interfaces.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betalyze.dataretrieval.domain.service.DataRetrievalCommandService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/data-retrieval")
@RequiredArgsConstructor
public class DataRetrievalController {

    private final DataRetrievalCommandService dataRetrievalCommandService;

    @PostMapping("/full-load")
    public ResponseEntity<String> fullLoad() {
        try {
            dataRetrievalCommandService.handleFullDataLoad();
            return ResponseEntity.ok("Full data load process started successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error starting full data load process: " + e.getMessage());
        }
    }
}
