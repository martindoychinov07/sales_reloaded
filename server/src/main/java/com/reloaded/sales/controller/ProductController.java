package com.reloaded.sales.controller;

import com.reloaded.sales.dto.ProductDto;
import com.reloaded.sales.model.Product;
import com.reloaded.sales.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "product", description = "product service")
@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true")
@RestController
@RequestMapping("/api/product")
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
  public ProductDto createProduct(
    @RequestBody ProductDto productDto
  ) {
    return toDto(productService.createProduct(toEntity(productDto)));
  }

  @PutMapping(
    value = "/updateProduct",
    consumes = "application/json",
    produces = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public ProductDto updateProduct(
    @RequestBody ProductDto productDto
  ) {
    return toDto(productService.updateProduct(toEntity(productDto)));
  }

  @DeleteMapping(
    value = "/deleteProduct",
    consumes = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public void deleteProduct(
    @RequestBody Integer id
  ) {
    productService.deleteProduct(id);
  }

  @GetMapping(
    value = "/findProduct",
    produces = "application/json"
  )
  public Page<ProductDto> findProduct(
    @RequestParam Optional<String> productCode,
    @RequestParam Optional<String> productName,
    @RequestParam Optional<String> productNote,
    @RequestParam Optional<Integer> page,
    @RequestParam Optional<Integer> size,
    @RequestParam Optional<String> sort,
    @RequestParam Optional<Sort.Direction> direction
  ) {
    PageRequest paging = PageRequest.of(
      page.orElse(0),
      size.orElse(20),
      sort.isPresent()
        ? Sort.by(
        Sort.by(direction.orElse(Sort.Direction.ASC), sort.get()).getOrderFor(sort.get()),
        Sort.Order.asc(Product.Fields.productId)
      )
        : Sort.by(Sort.Order.asc(Product.Fields.productId)
      )
    );

    return productService.findProductByCodeNameNote(
      productCode.orElse(null),
      productName.orElse(null),
      productNote.orElse(null),
      paging
    ).map(this::toDto);
  }

  @GetMapping(
    value = "/{id}",
    produces = "application/json"
  )
  public Optional<Product> getProductById(
    @PathVariable int id
  ) {
    return productService.getProductById(id);
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
