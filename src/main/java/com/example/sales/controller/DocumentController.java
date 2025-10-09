package com.example.sales.controller;

import com.example.sales.DTO.DocumentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.sales.service.DocumentService;
import com.example.sales.model.Document;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true")
@RestController
@RequestMapping("/document")
public class DocumentController {
    @Autowired
    DocumentService documentService;

    @PostMapping("/create")
    public ResponseEntity<String> createDocument(@RequestBody DocumentRequest documentCreateRequest) {
        return documentService.createDocument(documentCreateRequest);
    }

    @PatchMapping("/edit")
    public ResponseEntity<String> updateDocument(@RequestBody DocumentRequest documentCreateRequest) {
        return documentService.editDocument(documentCreateRequest);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteDocument(@RequestBody long id) {
        return documentService.deleteDocument(id);
    }

    @GetMapping("/all")
    public List<Document> getAllDocuments() {
        return documentService.getAllDocuments();
    }
}