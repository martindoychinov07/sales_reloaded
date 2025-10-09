package com.reloaded.sales.DTO;

import lombok.*;

import com.reloaded.sales.model.Operation;
import com.reloaded.sales.model.Item;
import com.reloaded.sales.model.Document;

@Data
public class OperationRequest {
    @Getter @Setter
    private Item item;

    @Getter @Setter
    private Document document;

    @Getter @Setter
    private Operation operation;
}
