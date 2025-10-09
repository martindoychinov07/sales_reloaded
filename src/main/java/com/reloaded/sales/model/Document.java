package com.reloaded.sales.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "document")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private long id;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private LocalDateTime docDate = LocalDateTime.now();

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private int docType;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private String docBook;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private String docTemplate;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private int docNumber;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private long supplierId;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private long customerId;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private String note;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private String docTags;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private String rpTags;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private String docRefs;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private int payment;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private int availability;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private long authorId;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private long assigneeId;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private int rows;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private int vat;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private String ccy;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private int rating;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private int total;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private int totalTax;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private String statusType;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private String statusNote;
}
