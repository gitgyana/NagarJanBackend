package com.nagarjan.app.controller;

import com.nagarjan.app.dtos.CreateGrievanceRequest;
import com.nagarjan.app.services.GrievanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/grievance")
@RequiredArgsConstructor
public class GrievanceController {

    private final GrievanceService service;

    @PostMapping
    public ResponseEntity<?> createGrievance(@RequestBody CreateGrievanceRequest request) {
        service.createGrievance(request);
        return ResponseEntity.ok("Grievance Created Successfully");
    }
}