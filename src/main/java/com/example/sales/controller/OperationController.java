package com.example.sales.controller;

import com.example.sales.DTO.OperationRequest;
import com.example.sales.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true")
@RestController
@RequestMapping("/operation")
public class OperationController {
    @Autowired
    OperationService operationService;

    @PostMapping("/create")
    public ResponseEntity<String> createOperation(@RequestBody OperationRequest addOperationRequest) {
        return operationService.createOperation(addOperationRequest);
    }
}
