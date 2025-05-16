package org.icet.learn.service.customer;

import org.icet.learn.dto.Product;

import java.util.List;

public interface CustomerProductService {
    List<Product> getAllProducts();
    List<Product> searchProductByName(String name);
}
