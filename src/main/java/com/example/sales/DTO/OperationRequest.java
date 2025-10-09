package com.example.sales.DTO;

import lombok.*;

import com.example.sales.model.Operation;
import com.example.sales.model.Item;
import com.example.sales.model.Document;

@Data
public class OperationRequest {
    @Getter @Setter
    private Item item;

    @Getter @Setter
    private Document document;

    @Getter @Setter
    private Operation operation;
}
