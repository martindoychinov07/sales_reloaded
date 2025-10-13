package com.reloaded.sales.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "partner")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@RequiredArgsConstructor
public class Partner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private long id;

    @Getter @Setter
    private long refId;

    @NonNull
    @Column(nullable = true)
    @Getter @Setter
    private LocalDateTime version = LocalDateTime.now();;

    @NonNull
    @Column(nullable = true)
    @Getter @Setter
    private String code;

    @NonNull
    @Column(nullable = true)
    @Getter @Setter
    private String name;

    @NonNull
    @Column(nullable = true)
    @Getter @Setter
    private String location;

    @NonNull
    @Column(nullable = true)
    @Getter @Setter
    private String address;

    @NonNull
    @Column(nullable = true)
    @Getter @Setter
    private String idTags;

    @NonNull
    @Column(nullable = true)
    @Getter @Setter
    private String cpTags;

    @NonNull
    @Column(nullable = true)
    @Getter @Setter
    private String rcvd;

    @NonNull
    @Column(nullable = true)
    @Getter @Setter
    private String noteProps;

    @NonNull
    @Column(nullable = true)
    @Getter @Setter
    private int rating;
}
