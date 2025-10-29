package com.reloaded.sales.controller;

import com.reloaded.sales.dto.ProductDto;
import com.reloaded.sales.model.Product;
import com.reloaded.sales.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true")
@RestController
@RequestMapping("/product")
public class ProductController {

  final ProductService productService;
  private final ModelMapper modelMapper;

  public ProductController(ProductService productService) {
    this.productService = productService;
    this.modelMapper = new ModelMapper();
  }

  @PostMapping("/create")
  @ResponseStatus(HttpStatus.CREATED)
  public ProductDto create(@RequestBody ProductDto productDto) {
    return toDto(productService.create(toEntity(productDto)));
  }

  @PutMapping("/update")
  @ResponseStatus(HttpStatus.OK)
  public ProductDto update(@RequestBody ProductDto productDto) {
    return toDto(productService.update(toEntity(productDto)));
  }

  @DeleteMapping("/delete")
  @ResponseStatus(HttpStatus.OK)
  public void delete(@RequestBody Integer id) {
    productService.delete(id);
  }

  @GetMapping("/{id}")
  public Optional<Product> getById(@PathVariable int id) {
    return productService.getById(id);
  }

  @GetMapping("/findAll")
  public Page<ProductDto> findAll(
          @RequestParam String name,
          @RequestParam Optional<String> code,
          Pageable paging) {
    return productService.findAllByNameCode(name, code.orElse(null), paging).map(this::toDto);
  }

  private Product toEntity(ProductDto dto) {
    return modelMapper.map(
            dto,
            Product.class
    );
  }

  private ProductDto toDto(Product entity) {
    return modelMapper.map(
            entity,
            ProductDto.class
    );
  }
}
