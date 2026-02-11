package com.reloaded.sales.controller;

import com.reloaded.sales.dto.ProductDto;
import com.reloaded.sales.dto.filter.ProductFilter;
import com.reloaded.sales.model.Product;
import com.reloaded.sales.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * REST controller for managing products.
 * Provides standard CRUD operations:
 *  - create
 *  - update
 *  - delete
 *  - find (with filter and pagination)
 *  - get by ID
 */
@Tag(name = "product", description = "product service") // Swagger documentation
@RestController
@RequestMapping("/api/product") // Base URL for product endpoints
@RequiredArgsConstructor
public class ProductController implements CrudController<ProductDto, ProductFilter, Product> {

  private final ProductService productService; // Service for business logic
  private final ModelMapper modelMapper;       // Used for Entity <-> DTO mapping

  /**
   * Creates a new product
   */
  @Operation(operationId = "createProduct")
  @Override
  public ProductDto create(@RequestBody ProductDto productDto) {
    return toDto(productService.createProduct(toEntity(productDto)));
  }

  /**
   * Updates an existing product by ID
   */
  @Operation(operationId = "updateProduct")
  @Override
  public ProductDto update(@PathVariable int id, @RequestBody ProductDto productDto) {
    Product product = toEntity(productDto);
    product.setProductId(id); // Ensure the ID comes from the path
    return toDto(productService.updateProduct(product));
  }

  /**
   * Deletes a product by ID
   */
  @Operation(operationId = "deleteProduct")
  @Override
  public void delete(@PathVariable int id) {
    productService.deleteProduct(id);
  }

  /**
   * Finds products using filter criteria (paginated)
   */
  @Operation(operationId = "findProduct")
  @Override
  public Page<ProductDto> find(@ParameterObject @ModelAttribute ProductFilter filter) {
    return productService.findProduct(filter).map(this::toDto);
  }

  /**
   * Returns a product by ID
   */
  @Operation(operationId = "getProductById")
  @Override
  public ProductDto getById(@PathVariable int id) {
    return toDto(productService.getProductById(id));
  }

  /**
   * Converts Product DTO to entity
   */
  public Product toEntity(ProductDto dto) {
    return modelMapper.map(dto, Product.class);
  }

  /**
   * Converts Product entity to DTO
   */
  public ProductDto toDto(Product entity) {
    return modelMapper.map(entity, ProductDto.class);
  }
}
