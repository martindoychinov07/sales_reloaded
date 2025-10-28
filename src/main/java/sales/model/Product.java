package sales.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldNameConstants
@Entity
@Table(name = "product")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_gen")
  @SequenceGenerator(name = "product_id_gen", sequenceName = "product_sequence", allocationSize = 1)
  @Column(name = "product_id", nullable = false)
  private Integer productId;

  @Column(name = "product_ref_id")
  private Integer productRefId;

  @Column(name = "product_state")
  private Integer productState;

  @Column(name = "version")
  private Instant version;

  @Column(name = "barcode", length = 50)
  private String barcode;

  @Column(name = "item", length = 200)
  private String item;

  @Column(name = "note", length = 200)
  private String note;

  @Column(name = "units")
  private Integer units;

  @Column(name = "measure", length = 30)
  private String measure;

  @Column(name = "available")
  private Integer available;

  @Column(name = "code", length = 50)
  private String code;

  @Column(name = "code1", length = 50)
  private String code1;

  @Column(name = "code2", length = 50)
  private String code2;

  @Column(name = "code3", length = 50)
  private String code3;

  @Column(name = "code4", length = 50)
  private String code4;

  @Column(name = "code5", length = 50)
  private String code5;

  @Column(name = "code6", length = 50)
  private String code6;

  @Column(name = "code7", length = 50)
  private String code7;

  @Column(name = "code8", length = 50)
  private String code8;

  @Column(name = "code9", length = 50)
  private String code9;

  @Column(name = "price", precision = 16, scale = 4)
  private BigDecimal price;

  @Column(name = "price1", precision = 16, scale = 4)
  private BigDecimal price1;

  @Column(name = "price2", precision = 16, scale = 4)
  private BigDecimal price2;

  @Column(name = "price3", precision = 16, scale = 4)
  private BigDecimal price3;

  @Column(name = "price4", precision = 16, scale = 4)
  private BigDecimal price4;

  @Column(name = "price5", precision = 16, scale = 4)
  private BigDecimal price5;

  @Column(name = "price6", precision = 16, scale = 4)
  private BigDecimal price6;

  @Column(name = "price7", precision = 16, scale = 4)
  private BigDecimal price7;

  @Column(name = "price8", precision = 16, scale = 4)
  private BigDecimal price8;

  @Column(name = "price9", precision = 16, scale = 4)
  private BigDecimal price9;

  @Column(name = "ware_id", length = 40)
  private String wareId;

}