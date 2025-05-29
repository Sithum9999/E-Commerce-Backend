package org.icet.learn.service.customer;

import lombok.RequiredArgsConstructor;
import org.icet.learn.dto.Product;
import org.icet.learn.dto.ProductDetail;
import org.icet.learn.entity.FaqEntity;
import org.icet.learn.entity.ProductEntity;
import org.icet.learn.entity.ReviewEntity;
import org.icet.learn.repository.FaqDao;
import org.icet.learn.repository.ProductDao;
import org.icet.learn.repository.ReviewDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerProductServiceImpl implements CustomerProductService {

    private final ProductDao productDao;
    private final FaqDao faqDao;
    private final ReviewDao reviewDao;

    public List<Product> getAllProducts() {
        List<ProductEntity> all = productDao.findAll();
        return all.stream().map(ProductEntity::getDto).collect(Collectors.toList());
    }

    public List<Product> searchProductByName(String name) {
        List<ProductEntity> all = productDao.findAllByNameContaining(name);
        return all.stream().map(ProductEntity::getDto).collect(Collectors.toList());
    }

    public ProductDetail getProductDetailById(Long productId) {
        Optional<ProductEntity> optionalProduct = productDao.findById(productId);
        if (optionalProduct.isPresent()) {
            List<FaqEntity> faqList = faqDao.findAllByProductId(productId);
            List<ReviewEntity> reviewsList = reviewDao.findAllByProductId(productId);

            ProductDetail productDetailDto = new ProductDetail();

            productDetailDto.setProductDto(optionalProduct.get().getDto());
            productDetailDto.setFaqDtoList(faqList.stream().map(FaqEntity::getFaqDto).collect(Collectors.toList()));
            productDetailDto.setReviewDtoList(reviewsList.stream().map(ReviewEntity::getDto).collect(Collectors.toList()));

            return productDetailDto;
        }
        return null;
    }


}
