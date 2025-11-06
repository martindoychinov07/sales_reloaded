package com.reloaded.sales.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldNameConstants
@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_gen")
    @SequenceGenerator(name = "user_id_gen", sequenceName = "user_sequence", allocationSize = 1)
    @Getter
    private long id;

    @NonNull
    @Column(nullable = false, unique = true)
    @Getter @Setter
    private String username;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private String password;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private String role = "user";

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private long code;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}