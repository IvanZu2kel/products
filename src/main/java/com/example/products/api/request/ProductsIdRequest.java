package com.example.products.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductsIdRequest {
    @JsonProperty("products_ids")
    private List<Long> productsIds;
}
