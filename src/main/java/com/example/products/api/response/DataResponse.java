package com.example.products.api.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Collection;

@Data
@Accessors(chain = true)
public class DataResponse<T> {
    private Collection<T> data;
}
