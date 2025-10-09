package com.example.sales.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "\"user\"")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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