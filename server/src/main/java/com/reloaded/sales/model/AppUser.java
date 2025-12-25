package com.reloaded.sales.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
@Table(name = "app_user")
public class AppUser {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_user_id_gen")
  @SequenceGenerator(name = "app_user_id_gen", sequenceName = "app_user_sequence", allocationSize = 1)
  @Column(name = "u_id", nullable = false)
  private Integer userId;

  @Size(max = 100)
  @Column(name = "u_username", length = 100)
  private String username;

  @Size(max = 200)
  @Column(name = "u_password", length = 200)
  private String password;

  @Size(max = 100)
  @Column(name = "u_role", length = 100)
  private String userRole;

  @Column(name = "u_expire_date")
  private Instant userExpireDate;

  @Column(name = "u_lock_date")
  private Instant userLockDate;

  @Size(max = 100)
  @Column(name = "u_fullname", length = 100)
  private String fullname;

}