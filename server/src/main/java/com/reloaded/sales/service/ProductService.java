package com.reloaded.sales.service;

import com.reloaded.sales.exception.NotFound;
import com.reloaded.sales.model.*;
import com.reloaded.sales.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ProductService {
  final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Product createProduct(Product product) {
    product.setProductState(ProductState.active);
    return productRepository.save(product);
  }

  public Product updateProduct(Product changes) {
    Product entity = productRepository
      .findById(changes.getProductId())
      .orElseThrow(() -> new NotFound("Product not found"));

    BeanUtils.copyProperties(changes, entity, Product.Fields.productId, Product.Fields.productState, Product.Fields.productAvailable);

    return productRepository.save(entity);
  }

  public void deleteProduct(Integer id) {
    Product product = productRepository.findById(id)
      .orElseThrow(() -> new NotFound("Product not found"));
    product.setProductState(ProductState.deleted);
    productRepository.save(product);
  }

  public Optional<Product> getProductById(Integer id) {
    return productRepository.findById(id);
  }

  @Transactional(readOnly = true)
  public Page<Product> findProductByCodeNameNote(String code, String name, String note, Pageable paging) {
    Product probe = Product.builder()
      .productCode(code)
      .productName(name)
      .productNote(note)
      .productState(ProductState.active)
      .build();

    final ExampleMatcher.GenericPropertyMatchers match = new ExampleMatcher.GenericPropertyMatchers();
    ExampleMatcher matcher = ExampleMatcher
      .matchingAll()
      .withIgnoreNullValues()
      .withMatcher(Product.Fields.productCode, match.contains().ignoreCase())
      .withMatcher(Product.Fields.productName, match.contains().ignoreCase())
      .withMatcher(Product.Fields.productNote, match.contains().ignoreCase());

    Example<Product> example = Example.of(probe, matcher);

    return productRepository.findAll(example, paging);
  }

}
