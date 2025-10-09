package com.reloaded.sales.DTO;

import com.reloaded.sales.model.Operation;
import com.reloaded.sales.model.Partner;
import lombok.*;

import com.reloaded.sales.model.Document;

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