package com.example.products.controller;

import com.example.products.api.request.ProductsIdRequest;
import com.example.products.api.response.IdResponse;
import com.example.products.api.response.list.ListListResponse;
import com.example.products.api.response.list.ListResponse;
import com.example.products.exception.NotFoundProductOrListException;
import com.example.products.service.ListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Контроллер для работы с корзиной")
@RequestMapping("/api/lists")
@RequiredArgsConstructor
public class ListController {
    private final ListService listService;

    @Operation(summary = "Получение всех корзин")
    @GetMapping
    public ResponseEntity<ListListResponse> getAllProducts() {
        return new ResponseEntity<>(listService.getAllLists(), HttpStatus.OK);
    }

    @Operation(summary = "Создание корзины")
    @PostMapping
    public ResponseEntity<ListResponse> editList(@RequestParam String name) {
        return new ResponseEntity<>(listService.editList(name), HttpStatus.OK);
    }

    @Operation(summary = "Изменение названия корзины по id")
    @PutMapping("/{id}")
    public ResponseEntity<ListResponse> changeList(@PathVariable Long id,
                                                   @RequestParam String name) throws NotFoundProductOrListException {
        return new ResponseEntity<>(listService.changeList(id, name), HttpStatus.OK);
    }

    @Operation(summary = "Удаление корзины")
    @DeleteMapping("/{id}")
    public ResponseEntity<IdResponse> deleteList(@PathVariable Long id) throws NotFoundProductOrListException {
        return new ResponseEntity<>(listService.deleteList(id), HttpStatus.OK);
    }

    @Operation(summary = "Добавление продуктов в корзину")
    @PutMapping("/{id}/products")
    public ResponseEntity<ListResponse> addedProductsOnList(@PathVariable Long id,
                                                            @RequestBody ProductsIdRequest ids) throws NotFoundProductOrListException {
        return new ResponseEntity<>(listService.addedProductsOnList(id, ids), HttpStatus.OK);
    }

    @Operation(summary = "Удаление продуктов из корзины")
    @DeleteMapping("/{id}/products")
    public ResponseEntity<ListResponse> deletedProductsOnList(@PathVariable Long id,
                                                              @RequestBody ProductsIdRequest ids) throws NotFoundProductOrListException {
        return new ResponseEntity<>(listService.deletedProductsOnList(id, ids), HttpStatus.OK);
    }
}
