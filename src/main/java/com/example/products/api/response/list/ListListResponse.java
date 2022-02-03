package com.example.products.api.response.list;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ListListResponse {
    private List<ListResponse> lists;
}
