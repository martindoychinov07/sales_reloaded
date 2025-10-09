package com.example.sales.repository;

import com.example.sales.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sales.model.Partner;

import java.util.List;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {
    Partner findByName(String name);
    List<Partner> findByRatingNot(int rating);
}
