package com.example.products.api.response.product;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ProductsListResponse {
    private List<ProductResponse> products;
}
