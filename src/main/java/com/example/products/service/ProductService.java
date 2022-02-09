package com.example.products.service;

import com.example.products.api.request.ProductRequest;
import com.example.products.api.response.DataResponse;
import com.example.products.api.response.IdResponse;
import com.example.products.api.response.product.ProductResponse;
import com.example.products.exception.EmptyNameException;
import com.example.products.exception.NotFoundProductOrListException;
import com.example.products.model.Product;
import com.example.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public DataResponse<ProductResponse> getAllProducts() {
        Collection<Product> allProducts = productRepository.findAll();
        Collection<ProductResponse> productResponse = new ArrayList<>();
        for (Product product : allProducts) {
            productResponse.add(new ProductResponse()
                    .setId(product.getId())
                    .setName(product.getName())
                    .setDescription(product.getDescription())
                    .setKcal(product.getKcal()));
        }
        return new DataResponse<ProductResponse>().setData(productResponse);
    }

    public IdResponse editProduct(ProductRequest productRequest) throws EmptyNameException {
        if (productRequest.getName().trim().isEmpty()) throw new EmptyNameException();
        Optional<Product> optionalProduct = productRepository.findByName(productRequest.getName());
        if (optionalProduct.isPresent()) {
            optionalProduct.get()
                    .setDescription(productRequest.getDescription())
                    .setKcal(productRequest.getKcal());
            Product save = productRepository.save(optionalProduct.get());
            return new IdResponse().setId(save.getId());
        } else {
            Product product = new Product()
                    .setName(productRequest.getName())
                    .setDescription(productRequest.getDescription())
                    .setKcal(productRequest.getKcal());
            Product save = productRepository.save(product);
            return new IdResponse().setId(save.getId());
        }
    }

    public IdResponse changeProduct(Long id, ProductRequest productRequest) throws NotFoundProductOrListException {
        Product product = getProduct(id);
        product
                .setName(productRequest.getName())
                .setDescription(productRequest.getDescription())
                .setKcal(productRequest.getKcal());
        Product save = productRepository.save(product);
        return new IdResponse().setId(save.getId());
    }

    public IdResponse deleteProduct(Long id) throws NotFoundProductOrListException {
        Product product = getProduct(id);
        productRepository.delete(product);
        return new IdResponse().setId(id);
    }

    private Product getProduct(Long id) throws NotFoundProductOrListException {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundProductOrListException("Product not found"));
    }
}
