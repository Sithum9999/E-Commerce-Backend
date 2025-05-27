package org.icet.learn.controller.admin;

import lombok.RequiredArgsConstructor;
import org.icet.learn.dto.Faq;
import org.icet.learn.dto.Product;
import org.icet.learn.service.admin.faq.FaqService;
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
    private final FaqService faqService;

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

    @GetMapping("/search/{name}")
    public ResponseEntity<List<Product>> getAllProductByName(@PathVariable String name) {
        List<Product> products = adminProductService.getAllProductByName(name);
        return ResponseEntity.ok(products);
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        boolean deleted = adminProductService.deleteProduct(productId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/faq/{productId}")
    public ResponseEntity<Faq> postFAQ(@PathVariable Long productId, @RequestBody Faq faq) {
        return ResponseEntity.status(HttpStatus.CREATED).body(faqService.postFAQ(productId, faq));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        Product productDto = adminProductService.getProductById(productId);
        if (productDto != null) {
            return ResponseEntity.ok(productDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable long productId, @ModelAttribute Product productDto) throws IOException {
        Product updatedProduct = adminProductService.updateProduct(productId, productDto);

        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
