package sales.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldNameConstants
@Entity
@Table(name = "partner")
public class Partner {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partner_id_gen")
  @SequenceGenerator(name = "partner_id_gen", sequenceName = "partner_sequence", allocationSize = 1)
  @Column(name = "partner_id", nullable = false)
  private Integer partnerId;

  @Column(name = "partner_ref_id")
  private Integer partnerRefId;

  @Column(name = "partner_state")
  private Integer partnerState;

  @Column(name = "version")
  private Instant version;

  @Column(name = "code", length = 30)
  private String code;

  @Column(name = "name", length = 200)
  private String name;

  @Column(name = "location", length = 200)
  private String location;

  @Column(name = "address", length = 300)
  private String address;

  @Column(name = "id_tags", length = 300)
  private String idTags;

  @Column(name = "cp_tags", length = 300)
  private String cpTags;

  @Column(name = "note_tags", length = 300)
  private String noteTags;

}