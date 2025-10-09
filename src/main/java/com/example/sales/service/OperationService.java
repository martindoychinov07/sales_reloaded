package com.example.sales.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sales.model.Operation;
import com.example.sales.DTO.OperationRequest;
import com.example.sales.repository.OperationRepository;
import com.example.sales.exception.DocumentNotFoundException;
import com.example.sales.exception.ItemNotFoundException;
import com.example.sales.exception.OperationNotFoundException;
import com.example.sales.model.Document;
import com.example.sales.model.Item;
import com.example.sales.repository.DocumentRepository;
import com.example.sales.repository.ItemRepository;

@Transactional
@Service
public class OperationService {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    OperationRepository operationRepository;

    @Autowired
    DocumentRepository documentRepository;

    public ResponseEntity<String> createOperation(OperationRequest createOperationRequest)
            throws ItemNotFoundException {
        Operation newOperation = new Operation();
        Item item = new Item();
        BeanUtils.copyProperties(createOperationRequest.getOperation(), newOperation);
        BeanUtils.copyProperties(createOperationRequest.getItem(), item);

        newOperation.setItem(item.getName());
        newOperation.setItemId(item.getId());
        newOperation.setDocId(createOperationRequest.getDocument().getId());

        Document document = documentRepository.findById(createOperationRequest.getDocument().getId())
                .orElseThrow(() -> new DocumentNotFoundException("Document not found"));

        switch (document.getDocType()) {
            case 1:
                item.setQuantity(item.getQuantity() + newOperation.getQuantity());
                break;
            case 2:
                item.setQuantity(item.getQuantity() - newOperation.getQuantity());
                break;
        }

        operationRepository.save(newOperation);
        itemRepository.save(item);

        return ResponseEntity.ok("Operation created successfully");
    }

    public ResponseEntity<String> editOperation(Operation op) {
        Operation operation = operationRepository.findById(op.getId())
                .orElseThrow(() -> new OperationNotFoundException("Operation not found"));

        BeanUtils.copyProperties(op, operation);

        operationRepository.save(operation);

        return ResponseEntity.ok("Operation edited successfully");
    }

//    public ResponseEntity<String> deleteOperation(long id)
//            throws OperationNotFoundException {
//        Operation operation = operationRepository.findByDocId(id);
//
//        if (operation == null) {
//            throw new OperationNotFoundException("Operation not found");
//        }
//
//        operationRepository.delete(operation);
//
//        Document document = documentRepository.findById(id)
//                .orElseThrow(() -> new DocumentNotFoundException("Document not found"));
//
//        Item item = itemRepository.findById(operation.getItemId())
//                .orElseThrow(() -> new ItemNotFoundException("Item not found"));
//
//        switch (document.getDocType()) {
//            case 1:
//                item.setQuantity(item.getQuantity() - operation.getQuantity());
//                break;
//            case 2:
//                item.setQuantity(item.getQuantity() + operation.getQuantity());
//                break;
//        }
//
//        operationRepository.save(operation);
//        documentRepository.save(document);
//        itemRepository.save(item);
//
//        return ResponseEntity.ok("Operation deleted successfully");
//    }
}
