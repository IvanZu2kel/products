package com.example.products.controller;

import com.example.products.api.request.ProductRequest;
import com.example.products.api.response.IdResponse;
import com.example.products.api.response.product.ProductsListResponse;
import com.example.products.exception.NotFoundProductOrListException;
import com.example.products.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Контроллер для работы с продуктами")
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductsController {
    private final ProductService productService;

    @Operation(summary = "Получение всех продуктов")
    @GetMapping
    public ResponseEntity<ProductsListResponse> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @Operation(summary = "Добавление продукта")
    @PostMapping
    public ResponseEntity<IdResponse> editProduct(@RequestBody ProductRequest productRequest) {
        return new ResponseEntity<>(productService.editProduct(productRequest), HttpStatus.OK);
    }

    @Operation(summary = "Изменение продукта по id")
    @PutMapping("/{id}")
    public ResponseEntity<IdResponse> changeProduct(@PathVariable Long id,
                                                    @RequestBody ProductRequest productRequest) throws NotFoundProductOrListException {
        return new ResponseEntity<>(productService.changeProduct(id, productRequest), HttpStatus.OK);
    }

    @Operation(summary = "Удаление продукта по id")
    @DeleteMapping("/{id}")
    public ResponseEntity<IdResponse> deleteProduct(@PathVariable Long id) throws NotFoundProductOrListException {
        return new ResponseEntity<>(productService.deleteProduct(id), HttpStatus.OK);
    }
}
