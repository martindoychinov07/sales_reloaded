package com.reloaded.sales.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "operation")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@RequiredArgsConstructor
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private long id;

    @NonNull
    @Column(nullable = true)
    @Getter @Setter
    private long docId;

    @NonNull
    @Column(nullable = true)
    @Getter @Setter
    private long row;

    @NonNull
    @Column(nullable = true)
    @Getter @Setter
    private long itemId;

    @NonNull
    @Column(nullable = true)
    @Getter @Setter
    private String barcode;

    @NonNull
    @Column(nullable = true)
    @Getter @Setter
    private String code;

    @NonNull
    @Column(nullable = true)
    @Getter @Setter
    private String item;

    @NonNull
    @Column(nullable = true)
    @Getter @Setter
    private long packageVar;

    @NonNull
    @Column(nullable = true)
    @Getter @Setter
    private String measure;

    @NonNull
    @Column(nullable = true)
    @Getter @Setter
    private int quantity;

    @NonNull
    @Column(nullable = true)
    @Getter @Setter
    private double price;

    @NonNull
    @Column(nullable = true)
    @Getter @Setter
    private double discount;

    @NonNull
    @Column(nullable = true)
    @Getter @Setter
    private double tax;

    @NonNull
    @Column(nullable = true)
    @Getter @Setter
    private int available;
}
