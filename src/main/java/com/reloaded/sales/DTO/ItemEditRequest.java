package com.reloaded.sales.DTO;

import lombok.*;

import com.reloaded.sales.model.Item;

public class ItemEditRequest {
    @Getter @Setter
    private Item item;
}
