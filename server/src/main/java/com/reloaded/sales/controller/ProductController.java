package com.reloaded.sales.controller;

import com.reloaded.sales.dto.ProductDto;
import com.reloaded.sales.model.Product;
import com.reloaded.sales.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "product", description = "product service")
@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true")
@RestController
@RequestMapping("/product")
public class ProductController {

  private final ProductService productService;
  private final ModelMapper modelMapper;

  public ProductController(ProductService productService) {
    this.productService = productService;
    this.modelMapper = new ModelMapper();
  }

  @PostMapping(
          value = "/createProduct",
          consumes = "application/json",
          produces = "application/json"
  )
  @ResponseStatus(HttpStatus.CREATED)
  public ProductDto createProduct(@RequestBody ProductDto productDto) {
    return toDto(productService.create(toEntity(productDto)));
  }

  @PutMapping(
          value = "/updateProduct",
          consumes = "application/json",
          produces = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public ProductDto updateProduct(@RequestBody ProductDto productDto) {
    return toDto(productService.update(toEntity(productDto)));
  }

  @DeleteMapping(
          value = "/deleteProduct",
          consumes = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public void deleteProduct(@RequestBody Integer id) {
    productService.delete(id);
  }

  @GetMapping(
          value = "/{id}",
          produces = "application/json"
  )
  public Optional<Product> getProductById(@PathVariable int id) {
    return productService.getById(id);
  }

  @GetMapping(
          value = "/findProduct",
          produces = "application/json"
  )
  public Page<ProductDto> findProduct(
    @RequestParam String name,
    @RequestParam Optional<String> code,
    @RequestParam Optional<Integer> page,
    @RequestParam Optional<Integer> size,
    @RequestParam Optional<String> sort,
    @RequestParam Optional<Sort.Direction> direction
  ) {
    PageRequest paging = PageRequest.ofSize(size.orElse(20));
    if (page.isPresent()) {
      paging = paging.withPage(page.get());
    }
    if (sort.isPresent()) {
      paging = paging.withSort(direction.orElse(Sort.Direction.ASC), sort.get());
    }
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
