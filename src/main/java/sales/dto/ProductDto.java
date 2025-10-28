package sales.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class ProductDto {
    private Integer productId;
    private Instant version;
    private String barcode;
    private String item;
    private String note;
    private Integer units;
    private String measure;
    private Integer available;
    private String code;
    private String code1;
    private String code2;
    private String code3;
    private String code4;
    private String code5;
    private String code6;
    private String code7;
    private String code8;
    private String code9;
    private BigDecimal price;
    private BigDecimal price1;
    private BigDecimal price2;
    private BigDecimal price3;
    private BigDecimal price4;
    private BigDecimal price5;
    private BigDecimal price6;
    private BigDecimal price7;
    private BigDecimal price8;
    private BigDecimal price9;
    private String wareId;
}
