package com.reloaded.sales.repository;

import com.reloaded.sales.model.Document;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reloaded.sales.model.Operation;

import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long>{
    public Operation findByDocId(long id);

    List<Document> findByItemId(long itemId, Pageable pageable);
}