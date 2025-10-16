package com.reloaded.sales.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "doc")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private long id;

    @Column(nullable = true)
    @Getter @Setter
    private LocalDateTime docDate = LocalDateTime.now();

    @Column(nullable = true)
    @Getter @Setter
    private int docType;

    @Column(nullable = true)
    @Getter @Setter
    private String docBook;

    @Column(nullable = true)
    @Getter @Setter
    private String docTemplate;

    @Column(nullable = true)
    @Getter @Setter
    private int docNumber;

    @Column(nullable = true)
    @Getter @Setter
    private long supplierId;

    @NonNull
    @Column(nullable = true)
    @Getter @Setter
    private long customerId;

    @Column(nullable = true)
    @Getter @Setter
    private String note;

    @Column(nullable = true)
    @Getter @Setter
    private String docTags;

    @Column(nullable = true)
    @Getter @Setter
    private String rpTags;

    @Column(nullable = true)
    @Getter @Setter
    private String docRefs;

    @Column(nullable = true)
    @Getter @Setter
    private int payment;

    @Column(nullable = true)
    @Getter @Setter
    private int availability;

    @Column(nullable = true)
    @Getter @Setter
    private long authorId;

    @Column(nullable = true)
    @Getter @Setter
    private long assigneeId;

    @Column(nullable = true)
    @Getter @Setter
    private int rows;

    @Column(nullable = true)
    @Getter @Setter
    private int vat;

    @Column(nullable = true)
    @Getter @Setter
    private String ccy;

    @Column(nullable = true)
    @Getter @Setter
    private int rating;

    @Column(nullable = true)
    @Getter @Setter
    private int total;

    @Column(nullable = true)
    @Getter @Setter
    private int totalTax;

    @Column(nullable = true)
    @Getter @Setter
    private String statusType;

    @Column(nullable = true)
    @Getter @Setter
    private String statusNote;
}
