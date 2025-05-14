package org.icet.learn.controller.admin;

import lombok.RequiredArgsConstructor;
import org.icet.learn.dto.Product;
import org.icet.learn.service.admin.product.AdminProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminProductController {

    private final AdminProductService adminProductService;

    @PostMapping("/product")
    public ResponseEntity<Product> addProduct(@ModelAttribute Product product) throws IOException {
        Product productAdd = adminProductService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productAdd);
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = adminProductService.getAllProducts();
        return ResponseEntity.ok(products);
    }



}
