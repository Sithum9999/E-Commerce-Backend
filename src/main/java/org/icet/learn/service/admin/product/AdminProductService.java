package org.icet.learn.service.admin.product;

import org.icet.learn.dto.Product;

import java.io.IOException;
import java.util.List;

public interface AdminProductService {
    Product addProduct(Product product) throws IOException;
    List<Product> getAllProducts();
    List<Product> getAllProductByName(String name);
}
