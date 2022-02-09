package com.example.products.service;

import com.example.products.api.request.ProductsIdRequest;
import com.example.products.api.response.IdResponse;
import com.example.products.api.response.DataResponse;
import com.example.products.api.response.list.ListResponse;
import com.example.products.api.response.product.ProductResponse;
import com.example.products.exception.EmptyNameException;
import com.example.products.exception.NotFoundProductOrListException;
import com.example.products.model.List;
import com.example.products.model.Product;
import com.example.products.repository.ListRepository;
import com.example.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ListService {
    private final ListRepository listRepository;
    private final ProductRepository productRepository;

    public DataResponse<ListResponse> getAllLists() {
        Collection<List> allLists = listRepository.findAll();
        Collection<ListResponse> listResponse = new ArrayList<>();
        for (List l : allLists) {
            listResponse.add(createListResponse(l));
        }
        return new DataResponse<ListResponse>().setData(listResponse);
    }

    public ListResponse editList(String name) throws EmptyNameException {
        Optional<List> optList = listRepository.findByName(name);
        if (optList.isPresent() || name.trim().isEmpty()) throw new EmptyNameException();
        List list = new List()
                .setName(name);
        List save = listRepository.save(list);
        return new ListResponse()
                .setId(save.getId())
                .setName(save.getName());
    }

    public ListResponse changeList(Long id, String name) throws NotFoundProductOrListException, EmptyNameException {
        Optional<List> optList = listRepository.findByName(name);
        if (optList.isPresent() || name.trim().isEmpty()) throw new EmptyNameException();
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
        Collection<Product> products = productRepository.findAllById(ids.getProductsIds());
        list.setProducts(products);
        List save = listRepository.save(list);
        return createListResponse(save);
    }

    public ListResponse deletedProductsOnList(Long id, ProductsIdRequest ids) throws NotFoundProductOrListException {
        List list = getListById(id);
        Collection<Product> products = productRepository.findAllById(ids.getProductsIds());
        list.getProducts().removeAll(products);
        List save = listRepository.save(list);
        return createListResponse(save);
    }

    private List getListById(Long id) throws NotFoundProductOrListException {
        return listRepository.findById(id).orElseThrow(() -> new NotFoundProductOrListException("List not found"));
    }

    private ListResponse createListResponse(List l) {
        Integer reduce = l.getProducts().stream().map(Product::getKcal).reduce(0, Integer::sum);
        return new ListResponse()
                .setId(l.getId())
                .setName(l.getName())
                .setProducts(getAllProducts(l.getProducts()))
                .setAllKcal(reduce);
    }

    private Collection<ProductResponse> getAllProducts(Collection<Product> products) {
        Collection<ProductResponse> productResponses = new ArrayList<>();
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
