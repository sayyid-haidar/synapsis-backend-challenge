package com.synapsis.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.synapsis.shop.dbo.Product;
import com.synapsis.shop.dto.ProductListResponse;
import com.synapsis.shop.exception.BadRequestException;
import com.synapsis.shop.repository.ProductRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/products")
    public ResponseEntity<ProductListResponse> getMethodName(
            @RequestParam(name = "page", required = false, defaultValue = "1") @Min(1) @Valid Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") @Min(1) @Valid Integer size,
            @RequestParam(name = "search", required = false) String search) {

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Product> products = this.productRepository.findAll(pageable);

        ProductListResponse response = ProductListResponse.builder()
                .totalData(products.getTotalElements())
                .totalPage(products.getTotalPages())
                .sizePage(products.getSize())
                .data(products.getContent())
                .build();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Product> getMethodName(@PathVariable Integer productId) throws Exception {

        Product product = this.productRepository.findById(productId)
                .orElseThrow(BadRequestException::new);

        return ResponseEntity.ok().body(product);
    }

}
