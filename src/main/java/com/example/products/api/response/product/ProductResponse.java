package com.example.products.api.response.product;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProductResponse {
    private long id;
    private String name;
    private String description;
    private int kcal;
}
