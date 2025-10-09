package com.reloaded.sales.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "item")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@RequiredArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private long id;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private String barcode;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private String code;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private String code1;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private String code2;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private String code3;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private String code4;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private String code5;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private String code6;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private String code7;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private String code8;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private String code9;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private String note;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private String name;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private long packageVar;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private String measure;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private int quantity;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private double price;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private double price1;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private double price2;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private double price3;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private double price4;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private double price5;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private double price6;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private double price7;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private double price8;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private double price9;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private int rating;
}
