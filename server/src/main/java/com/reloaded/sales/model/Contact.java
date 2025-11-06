package com.reloaded.sales.model;

import com.reloaded.sales.converter.GenericEnumConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldNameConstants;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldNameConstants
@Entity
@Table(name = "contact")
public class Contact {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_id_gen")
  @SequenceGenerator(name = "contact_id_gen", sequenceName = "contact_sequence", allocationSize = 1)
  @Column(name = "c_id", nullable = false)
  private Integer contactId;

  @Column(name = "c_version")
  private Integer contactVersion;

  @Column(name = "c_ref_id")
  private Integer contactRefId;

  @Column(name = "c_state")
  private ContactState contactState;

  @Size(max = 30)
  @Column(name = "c_code", length = 30)
  private String contactCode;

  @Size(max = 200)
  @Column(name = "c_name", length = 200)
  private String contactName;

  @Size(max = 200)
  @Column(name = "c_location", length = 200)
  private String contactLocation;

  @Size(max = 300)
  @Column(name = "c_address", length = 300)
  private String contactAddress;

  @Size(max = 300)
  @Column(name = "c_note", length = 300)
  private String contactNote;

  @Size(max = 100)
  @Column(name = "c_tag_uic", length = 100)
  private String contactTagUic;

  @Size(max = 100)
  @Column(name = "c_tag_vat", length = 100)
  private String contactTagVat;

  @Size(max = 100)
  @Column(name = "c_tag_tai", length = 100)
  private String contactTagTai;

  @Size(max = 100)
  @Column(name = "c_tag_own", length = 100)
  private String contactTagOwn;

  @Size(max = 100)
  @Column(name = "c_tag_resp", length = 100)
  private String contactTagResp;

}