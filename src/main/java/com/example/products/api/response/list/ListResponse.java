package com.example.products.api.response.list;

import com.example.products.api.response.product.ProductResponse;
import com.example.products.model.Product;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.List;

@Data
@Accessors(chain = true)
public class ListResponse {
    private long id;
    private String name;
    private Collection<ProductResponse> products;
    private int allKcal;
}
