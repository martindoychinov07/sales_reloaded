package com.reloaded.sales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reloaded.sales.model.Document;

import java.util.List;

@Repository
public interface DocumentRepository   extends JpaRepository<Document, Long> {
    List<Document> findByRatingNot(int rating);
}
