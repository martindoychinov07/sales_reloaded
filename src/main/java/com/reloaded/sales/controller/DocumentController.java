package com.reloaded.sales.controller;

import com.reloaded.sales.DTO.DocumentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.reloaded.sales.service.DocumentService;
import com.reloaded.sales.model.Document;

import java.time.LocalDateTime;
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

    @PutMapping("/edit")
    public ResponseEntity<String> updateDocument(@RequestBody DocumentRequest documentCreateRequest) {
        return documentService.editDocument(documentCreateRequest);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteDocument(@RequestBody long id) {
        return documentService.deleteDocument(id);
    }

    @GetMapping("/all")
    public List<Document> getAllDocuments(@RequestParam int page, @RequestParam int size) {
        return documentService.getAllDocuments(page, size);
    }

    @GetMapping("/customerId")
    public List<Document> getByCustomerId(@RequestBody long customerId, @RequestParam int page, @RequestParam int size) {
        return documentService.getByCustomerId(customerId, page, size);
    }

    @GetMapping("/itemId")
    public List<Document> getByItemId(@RequestBody long itemId, @RequestParam int page, @RequestParam int size) {
        return documentService.getByItemId(itemId, page, size);
    }

    @GetMapping("/date")
    public List<Document> getByDateBetween(@RequestBody LocalDateTime start, @RequestBody LocalDateTime end, @RequestParam int page, @RequestParam int size) {
        return documentService.getByDateBetween(start, end, page, size);
    }
}