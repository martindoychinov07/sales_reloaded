package com.reloaded.sales.service;

import com.reloaded.sales.exception.NotFound;
import com.reloaded.sales.model.Product;
import com.reloaded.sales.model.ProductState;
import com.reloaded.sales.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
  final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Product create(Product product) {
    return productRepository.save(product);
  }

  public Product update(Product changes) {
    Product entity = productRepository
      .findById(changes.getProductId())
      .orElseThrow(() -> new NotFound("Product not found"));

    BeanUtils.copyProperties(changes, entity, "id");

    return productRepository.save(entity);
  }

  public void delete(Integer id) {
    Product product = productRepository.findById(id)
      .orElseThrow(() -> new NotFound("Product not found"));

    productRepository.delete(product);
  }

  public Optional<Product> getById(Integer id) {
    return productRepository.findById(id);
  }

  public Page<Product> findAllByNameCode(String name, String code, Pageable paging) {
    Product probe = Product.builder()
      .productName(name)
      .productCode(code)
      .productState(ProductState.active)
      .build();

    final ExampleMatcher.GenericPropertyMatchers match = new ExampleMatcher.GenericPropertyMatchers();
    final Product.Fields field = new Product.Fields();
    ExampleMatcher matcher = ExampleMatcher
      .matchingAll()
      .withIgnoreNullValues()
      .withMatcher(field.productName, match.contains().ignoreCase())
      .withMatcher(field.productCode, match.contains().ignoreCase())
      .withMatcher(field.productCode, match.exact());

    Example<Product> example = Example.of(probe, matcher);

    return productRepository.findAll(example, paging);
  }
}
