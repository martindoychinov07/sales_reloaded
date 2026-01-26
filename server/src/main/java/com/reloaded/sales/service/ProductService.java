package com.reloaded.sales.service;

import com.reloaded.sales.dto.filter.ProductFilter;
import com.reloaded.sales.exception.NotFound;
import com.reloaded.sales.model.*;
import com.reloaded.sales.repository.ProductRepository;
import com.reloaded.sales.util.ServiceUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static com.reloaded.sales.util.ServiceUtils.*;

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

    spec.and(eq(Product.Fields.productState, ProductState.active));
    spec.and(anyLike(filter.getProductName(), Product.Fields.productName));
    spec.and(between(Product.Fields.productAvailable, filter.getFromAvailable(), filter.getToAvailable()));
    spec.and(anyLike(filter.getProductText(), Product.Fields.productName, Product.Fields.productNote, Product.Fields.productCode, Product.Fields.productBarcode));

    return productRepository.findAll(spec, paging);
  }

}
