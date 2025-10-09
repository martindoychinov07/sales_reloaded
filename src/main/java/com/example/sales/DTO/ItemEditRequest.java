package com.example.sales.DTO;

import lombok.*;

import com.example.sales.model.Item;

public class ItemEditRequest {
    @Getter @Setter
    private Item item;
}
