package com.reloaded.sales.controller;

import com.reloaded.sales.DTO.OperationRequest;
import com.reloaded.sales.model.Document;
import com.reloaded.sales.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/itemId")
    public List<Document> getByItemId(@RequestBody long itemId, @RequestParam int page, @RequestParam int size) {
        return operationService.getByItemId(itemId, page, size);
    }
}
