package com.reloaded.sales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reloaded.sales.model.Operation;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long>{
    public Operation findByDocId(long id);
}