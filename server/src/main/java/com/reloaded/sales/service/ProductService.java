/*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 */
package com.reloaded.sales.service;

import com.reloaded.sales.dto.filter.ProductFilter;
import com.reloaded.sales.exception.NotFound;
import com.reloaded.sales.model.*;
import com.reloaded.sales.repository.ProductRepository;
import com.reloaded.sales.security.AuditUtils;
import com.reloaded.sales.util.ServiceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static com.reloaded.sales.util.ServiceUtils.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  public Product createProduct(Product product) {
    product.setProductId(null);
    product.setProductState(ProductState.active);
    return productRepository.save(product);
  }

  public Product updateProduct(Product changes) {
    Product entity = productRepository
      .findById(changes.getProductId())
      .orElseThrow(() -> new NotFound("Product not found"));

    BeanUtils.copyProperties(
      changes,
      entity,
      Product.Fields.productId,
      Product.Fields.productState
//      Product.Fields.productAvailable
    );

    return productRepository.save(entity);
  }

  public void deleteProduct(Integer id) {
    Product product = productRepository.findById(id)
      .orElseThrow(() -> new NotFound("Product not found"));

    if (gt(product.getProductAvailable(), BigDecimal.ZERO)) {
      throw new IllegalStateException("Product with availability cannot be deleted");
    }

    product.setProductState(ProductState.deleted);
    productRepository.save(product);
  }

  @Transactional(readOnly = true)
  public Product getProductById(Integer id) {
    return productRepository.findById(id).orElseThrow(() -> new NotFound("Product not found"));
  }

  final List<Sort.Order> defaultSort = List.of(
    Sort.Order.asc(Product.Fields.productName),
    Sort.Order.asc(Product.Fields.productUnits),
    Sort.Order.asc(Product.Fields.productId)
  );

  @Transactional(readOnly = true)
  public Page<Product> findProduct(ProductFilter filter) {
    PageRequest paging = ServiceUtils.paging(filter, defaultSort);

    Specification<Product> spec = (root, query, cb) -> cb.conjunction();

    spec = spec.and(eq(ProductState.active, r -> r.get(Product.Fields.productState)));
    spec =  spec.and(anyLike(filter.getProductName(), r -> r.get(Product.Fields.productName)));
    spec =  spec.and(anyLike(filter.getProductNote(), r -> r.get(Product.Fields.productNote)));
    spec = spec.and(between(filter.getFromAvailable(), filter.getToAvailable(), r -> r.get(Product.Fields.productAvailable)));
    spec = spec.and(anyLike(filter.getProductText(),
    r -> r.get(Product.Fields.productName),
    r -> r.get(Product.Fields.productNote),
    r -> r.get(Product.Fields.productCode),
    r -> r.get(Product.Fields.productBarcode)
    ));

    return productRepository.findAll(spec, paging);
  }

}
