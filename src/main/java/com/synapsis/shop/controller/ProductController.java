package com.synapsis.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.synapsis.shop.dbo.Product;
import com.synapsis.shop.dbo.ProductCategory;
import com.synapsis.shop.dto.ProductListResponse;
import com.synapsis.shop.exception.BadRequestException;
import com.synapsis.shop.repository.ProductCategoryRepository;
import com.synapsis.shop.repository.ProductRepository;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@CrossOrigin(origins = "*")
@CacheConfig(cacheNames = "product")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @GetMapping("/product-categories")
    @SecurityRequirements
    @Cacheable
    public ResponseEntity<List<ProductCategory>> getProductCategories() {

        List<ProductCategory> categories = this.productCategoryRepository.findAll();

        return ResponseEntity.ok().body(categories);
    }

    @GetMapping("/products")
    @SecurityRequirements
    @Cacheable
    public ResponseEntity<ProductListResponse> getProducts(
            @RequestParam(name = "page", required = false, defaultValue = "1") @Min(1) @Valid Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") @Min(1) @Valid Integer size,
            @RequestParam(name = "category_id", required = false) Integer categoryId) {

        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Product> products = categoryId != null
                ? this.productRepository.findByCategoryId(categoryId, pageable)
                : this.productRepository.findAll(pageable);

        ProductListResponse response = ProductListResponse.builder()
                .totalData(products.getTotalElements())
                .totalPage(products.getTotalPages())
                .sizePage(products.getSize())
                .data(products.getContent())
                .build();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/product/{product_id}")
    @SecurityRequirements
    public ResponseEntity<Product> getMethodName(@PathVariable(name = "product_id") Integer productId)
            throws Exception {

        Product product = this.productRepository.findById(productId)
                .orElseThrow(BadRequestException::new);

        return ResponseEntity.ok().body(product);
    }

}
