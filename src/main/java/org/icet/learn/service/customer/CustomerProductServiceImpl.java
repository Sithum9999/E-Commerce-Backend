package org.icet.learn.service.customer;

import lombok.RequiredArgsConstructor;
import org.icet.learn.dto.Product;
import org.icet.learn.entity.ProductEntity;
import org.icet.learn.repository.ProductDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerProductServiceImpl implements CustomerProductService {

    private final ProductDao productDao;

    public List<Product> getAllProducts() {
        List<ProductEntity> all = productDao.findAll();
        return all.stream().map(ProductEntity::getDto).collect(Collectors.toList());
    }

    public List<Product> searchProductByName(String name) {
        List<ProductEntity> all = productDao.findAllByNameContaining(name);
        return all.stream().map(ProductEntity::getDto).collect(Collectors.toList());
    }
}
