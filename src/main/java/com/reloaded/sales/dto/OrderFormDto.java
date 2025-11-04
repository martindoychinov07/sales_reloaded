package com.reloaded.sales.dto;

import com.reloaded.sales.model.OrderEntry;
import com.reloaded.sales.model.Partner;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
public final class OrderFormDto {
    private Integer id;
    private Integer orderState;
    private Instant orderDate;
    private String orderBook;
    private Long orderNum;
    private Integer orderType;
    private String orderView;
    private Integer userId;
    private PartnerDto supplier;
    private PartnerDto customer;
    private String note;
    private String orderTags;
    private String paymentTags;
    private Integer availability;
    private Integer rows;
    private BigDecimal vat;
    private String ccy;
    private BigDecimal rate;
    private BigDecimal total;
    private BigDecimal totalTax;
    private List<OrderEntryDto> orders;
}
