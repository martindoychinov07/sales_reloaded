package com.example.sales.DTO;

import com.example.sales.model.Operation;
import com.example.sales.model.Partner;
import lombok.*;

import com.example.sales.model.Document;

import java.util.List;

public class DocumentRequest {
    @Getter @Setter
    private Document document;

    @Getter @Setter
    private Partner supplier;

    @Getter @Setter
    private Partner customer;

    @Getter @Setter
    private List<Operation> operations;
}