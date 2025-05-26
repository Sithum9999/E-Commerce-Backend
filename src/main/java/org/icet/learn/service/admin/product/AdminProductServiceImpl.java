package org.icet.learn.service.admin.product;

import lombok.RequiredArgsConstructor;
import org.icet.learn.dto.Product;
import org.icet.learn.entity.CategoryEntity;
import org.icet.learn.entity.ProductEntity;
import org.icet.learn.repository.CategoryDao;
import org.icet.learn.repository.ProductDao;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminProductServiceImpl implements AdminProductService {

    private final ProductDao productDao;

    private final CategoryDao categoryDao;

    public Product addProduct(Product product) throws IOException {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(product.getName());
        productEntity.setDescription(product.getDescription());
        productEntity.setPrice(product.getPrice());
        productEntity.setImg(product.getImg().getBytes());

        CategoryEntity categoryEntity = categoryDao.findById(product.getCategoryId()).orElseThrow();

        productEntity.setCategoryEntity(categoryEntity);
        return productDao.save(productEntity).getDto();
    }

    public List<Product> getAllProducts() {
        List<ProductEntity> all = productDao.findAll();
        return all.stream().map(ProductEntity::getDto).collect(Collectors.toList());
    }

    public List<Product> getAllProductByName(String name) {
        List<ProductEntity> all = productDao.findAllByNameContaining(name);
        return all.stream().map(ProductEntity::getDto).collect(Collectors.toList());
    }

    public boolean deleteProduct(Long id) {
        Optional<ProductEntity> optionalProduct = productDao.findById(id);
        if (optionalProduct.isPresent()) {
            productDao.deleteById(id);
            return true;
        }
        return false;
    }

    public Product getProductById(Long productId) {
        Optional<ProductEntity> optionalProduct = productDao.findById(productId);
        if (optionalProduct.isPresent()) {
            return optionalProduct.get().getDto();
        } else {
            return null;
        }
    }

    public Product updateProduct(Long productId, Product productDto) throws IOException {
        Optional<ProductEntity> optionalProduct = productDao.findById(productId);
        Optional<CategoryEntity> optionalCategory = categoryDao.findById(productDto.getCategoryId());

        if (optionalProduct.isPresent() && optionalCategory.isPresent()) {
            ProductEntity product = optionalProduct.get();

            product.setName(productDto.getName());
            product.setPrice(productDto.getPrice());
            product.setDescription(productDto.getDescription());
            product.setCategoryEntity(optionalCategory.get());
            if (productDto.getImg() != null) {
                product.setImg(productDto.getImg().getBytes());
            }

            return productDao.save(product).getDto();
        } else {
            return null;
        }
    }

}
