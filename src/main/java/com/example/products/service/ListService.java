package com.example.products.service;

import com.example.products.api.request.ProductsIdRequest;
import com.example.products.api.response.IdResponse;
import com.example.products.api.response.list.ListListResponse;
import com.example.products.api.response.list.ListResponse;
import com.example.products.api.response.product.ProductResponse;
import com.example.products.exception.NotFoundProductOrListException;
import com.example.products.model.List;
import com.example.products.model.Product;
import com.example.products.repository.ListRepository;
import com.example.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ListService {
    private final ListRepository listRepository;
    private final ProductRepository productRepository;

    public ListListResponse getAllLists() {
        java.util.List<List> allLists = listRepository.findAll();
        java.util.List<ListResponse> listResponse = new ArrayList<>();
        for (List l : allLists) {
            listResponse.add(createListResponse(l));
        }
        return new ListListResponse().setLists(listResponse);
    }

    public ListResponse editList(String name) {
        List list = new List()
                .setName(name);
        List save = listRepository.save(list);
        return new ListResponse()
                .setId(save.getId())
                .setName(save.getName());
    }

    public ListResponse changeList(Long id, String name) throws NotFoundProductOrListException {
        List list = getListById(id);
        list.setName(name);
        List save = listRepository.save(list);
        return new ListResponse()
                .setId(save.getId())
                .setName(save.getName());
    }

    public IdResponse deleteList(Long id) throws NotFoundProductOrListException {
        List list = getListById(id);
        listRepository.delete(list);
        return new IdResponse().setId(id);
    }

    public ListResponse addedProductsOnList(Long id, ProductsIdRequest ids) throws NotFoundProductOrListException {
        List list = getListById(id);
        java.util.List<Product> products = productRepository.findAllById(ids.getProductsIds());
        list.setProducts(products);
        List save = listRepository.save(list);
        return createListResponse(save);
    }

    public ListResponse deletedProductsOnList(Long id, ProductsIdRequest ids) throws NotFoundProductOrListException {
        List list = getListById(id);
        java.util.List<Product> products = productRepository.findAllById(ids.getProductsIds());
        list.getProducts().removeAll(products);
        List save = listRepository.save(list);
        return createListResponse(save);
    }

    private List getListById(Long id) throws NotFoundProductOrListException {
        return listRepository.findById(id).orElseThrow(NotFoundProductOrListException::new);
    }

    private ListResponse createListResponse(List l) {
        Integer reduce = l.getProducts().stream().map(Product::getKcal).reduce(0, Integer::sum);
        return new ListResponse()
                .setId(l.getId())
                .setName(l.getName())
                .setProducts(getAllProducts(l.getProducts()))
                .setAllKcal(reduce);
    }

    private java.util.List<ProductResponse> getAllProducts(java.util.List<Product> products) {
        java.util.List<ProductResponse> productResponses = new ArrayList<>();
        for (Product p : products) {
            productResponses.add(new ProductResponse()
                    .setId(p.getId())
                    .setName(p.getName())
                    .setDescription(p.getDescription())
                    .setKcal(p.getKcal()));
        }
        return productResponses;
    }
}
