package com.example.products.api.request;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProductRequest {
    private String name;
    private String description;
    private int kcal;
}
