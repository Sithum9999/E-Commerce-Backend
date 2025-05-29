package org.icet.learn.service.customer;

import org.icet.learn.dto.Product;
import org.icet.learn.dto.ProductDetail;

import java.util.List;

public interface CustomerProductService {

    List<Product> getAllProducts();

    List<Product> searchProductByName(String name);

    ProductDetail getProductDetailById(Long productId);

}