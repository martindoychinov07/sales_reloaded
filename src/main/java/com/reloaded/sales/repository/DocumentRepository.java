package com.reloaded.sales.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reloaded.sales.model.Document;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByRatingNot(int rating, Pageable pageable);
    List<Document> findByCustomerId(long customerId, Pageable pageable);
    List<Document> findByItemId(long itemId, Pageable pageable);
    List<Document> findByDocDateBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
}
