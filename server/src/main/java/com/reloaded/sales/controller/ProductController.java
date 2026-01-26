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

@Tag(name = "product", description = "product service")
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController implements CrudController<ProductDto, ProductFilter, Product> {

  private final ProductService productService;
  private final ModelMapper modelMapper;

  @Operation(operationId = "createProduct")
  @Override
  public ProductDto create(@RequestBody ProductDto productDto) {
    return toDto(productService.createProduct(toEntity(productDto)));
  }

  @Operation(operationId = "updateProduct")
  @Override
  public ProductDto update(@PathVariable int id, @RequestBody ProductDto productDto) {
    Product product = toEntity(productDto);
    product.setProductId(id);
    return toDto(productService.updateProduct(product));
  }

  @Operation(operationId = "deleteProduct")
  @Override
  public void delete(@PathVariable int id) {
    productService.deleteProduct(id);
  }

  @Operation(operationId = "findProduct")
  @Override
  public Page<ProductDto> find(@ParameterObject @ModelAttribute ProductFilter filter) {
    return productService.findProduct(filter).map(this::toDto);
  }

  @Operation(operationId = "getProductById")
  @Override
  public ProductDto getById(@PathVariable int id) {
    return toDto(productService.getProductById(id));
  }

  public Product toEntity(ProductDto dto) {
    return modelMapper.map(dto, Product.class);
  }

  public ProductDto toDto(Product entity) {
    return modelMapper.map(entity, ProductDto.class);
  }
}
