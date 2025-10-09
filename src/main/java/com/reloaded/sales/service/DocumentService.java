package com.reloaded.sales.service;

import com.reloaded.sales.exception.DocumentNotFoundException;
import com.reloaded.sales.model.Operation;
import com.reloaded.sales.repository.OperationRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reloaded.sales.model.Document;
import com.reloaded.sales.DTO.DocumentRequest;
import com.reloaded.sales.repository.DocumentRepository;
import com.reloaded.sales.repository.PartnerRepository;
import com.reloaded.sales.model.Partner;
import com.reloaded.sales.exception.PartnerNotFoundException;

import java.util.List;

@Transactional
@Service
public class DocumentService {
    @Autowired
    PartnerRepository partnerRepository;

    @Autowired
    OperationRepository operationRepository;

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    OperationService operationService;

    public ResponseEntity<String> createDocument(DocumentRequest createDocumentRequest)
            throws PartnerNotFoundException {
        Partner customer = partnerRepository.findById(createDocumentRequest.getCustomer().getId())
                .orElseThrow(() -> new PartnerNotFoundException("Customer not found"));

        Partner supplier = partnerRepository.findById(createDocumentRequest.getSupplier().getId())
                .orElseThrow(() -> new PartnerNotFoundException("Supplier not found"));

        Document document = createDocumentRequest.getDocument();
        document.setCustomerId(customer.getId());
        document.setSupplierId(supplier.getId());
        documentRepository.save(document);

        for (Operation op : createDocumentRequest.getOperations()) {
            op.setDocId(document.getId());
        }

        operationRepository.saveAll(createDocumentRequest.getOperations());

        return ResponseEntity.ok("Document created successfully");
    }

    public ResponseEntity<String> editDocument(DocumentRequest documentCreateRequest)
            throws PartnerNotFoundException, DocumentNotFoundException {
        Document document = documentRepository.findById(documentCreateRequest.getDocument().getId())
                .orElseThrow(() -> new DocumentNotFoundException("Document not found"));

        Partner customer = partnerRepository.findById(documentCreateRequest.getCustomer().getId())
                .orElseThrow(() -> new PartnerNotFoundException("Customer not found"));

        Partner supplier = partnerRepository.findById(documentCreateRequest.getSupplier().getId())
                .orElseThrow(() -> new PartnerNotFoundException("Supplier not found"));

        BeanUtils.copyProperties(documentCreateRequest.getDocument(), document);
        document.setCustomerId(customer.getId());
        document.setSupplierId(supplier.getId());
        documentRepository.save(document);

        return ResponseEntity.ok("Document created successfully");
    }

    public ResponseEntity<String> deleteDocument(long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new DocumentNotFoundException("Document not found"));

        document.setRating(0);

        return ResponseEntity.ok("Document deleted successfully");
    }

    @Transactional(readOnly = true)
    public List<Document> getAllDocuments() {
        return documentRepository.findByRatingNot(0);
    }
}