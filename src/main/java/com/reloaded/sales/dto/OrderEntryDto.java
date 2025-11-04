package com.reloaded.sales.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderEntryDto {
    private Integer entryId;
    private Integer orderId;
    private Integer orderRow;
    private Integer productId;
    private String barcode;
    private String code;
    private String label;
    private Integer units;
    private String measure;
    private Integer available;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal discount;
    private BigDecimal tax;
    private String wareId;
}
