package org.icet.learn.controller.customer;

import lombok.RequiredArgsConstructor;
import org.icet.learn.dto.Product;
import org.icet.learn.dto.ProductDetail;
import org.icet.learn.service.customer.CustomerProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerProductController {

    private final CustomerProductService customerProductService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = customerProductService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<Product>> getAllProductByName(@PathVariable String name) {
        List<Product> products = customerProductService.searchProductByName(name);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductDetail> getProductDetailById(@PathVariable Long productId) {
        ProductDetail productDetailDto = customerProductService.getProductDetailById(productId);
        if (productDetailDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDetailDto);
    }

}
