package com.reloaded.sales.repository;

import com.reloaded.sales.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
  Optional<Product> findById(int id);

  Page<Product> getPageByProductState(int state, Pageable paging);
  Slice<Product> getSliceByProductState(int state, Pageable paging);
}
