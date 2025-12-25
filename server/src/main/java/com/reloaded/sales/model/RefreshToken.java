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
@Table(name = "refresh_token")
public class RefreshToken {
  @Id
  @Column(name = "r_username", nullable = false)
  private String refreshUsername;

  @Size(max = 200)
  @Column(name = "r_token", length = 200)
  private String refreshToken;

  @Column(name = "r_expire_date")
  private Instant refreshExpireDate;
}