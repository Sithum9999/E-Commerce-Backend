package org.icet.learn.service.customer.review;

import lombok.RequiredArgsConstructor;
import org.icet.learn.dto.OrderedProductsResponse;
import org.icet.learn.dto.Product;
import org.icet.learn.dto.Review;
import org.icet.learn.entity.*;
import org.icet.learn.repository.OrderDao;
import org.icet.learn.repository.ProductDao;
import org.icet.learn.repository.ReviewDao;
import org.icet.learn.repository.UserDao;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final OrderDao orderDao;
    private final ProductDao productDao;
    private final UserDao userDao;
    private final ReviewDao reviewDao;

    public OrderedProductsResponse getOrderedProductsDetailsByOrderId(Long orderId) {
        Optional<OrderEntity> optionalOrder = orderDao.findById(orderId);
        OrderedProductsResponse orderedProductsResponseDto = new OrderedProductsResponse();

        if (optionalOrder.isPresent()) {
            orderedProductsResponseDto.setOrderAmount(optionalOrder.get().getAmount());

            List<Product> productDtoList = new ArrayList<>();
            for (CartItemsEntity cartItems : optionalOrder.get().getCartItems()) {
                Product productDto = new Product();
                productDto.setId(cartItems.getProduct().getId());
                productDto.setName(cartItems.getProduct().getName());
                productDto.setPrice(cartItems.getPrice());
                productDto.setQuantity(cartItems.getQuantity());

                productDto.setByteImg(cartItems.getProduct().getImg());
                productDtoList.add(productDto);
            }
            orderedProductsResponseDto.setProductDtoList(productDtoList);
        }
        return orderedProductsResponseDto;
    }

    public Review giveReview(Review review) throws IOException {
        Optional<ProductEntity> optionalProduct = productDao.findById(review.getProductId());
        Optional<UserEntity> optionalUser = userDao.findById(review.getUserId());

        if (optionalProduct.isPresent() && optionalUser.isPresent()) {
            ReviewEntity reviewEntity = new ReviewEntity();
            reviewEntity.setRating(review.getRating());
            reviewEntity.setDescription(review.getDescription());
            reviewEntity.setUser(optionalUser.get());
            reviewEntity.setProduct(optionalProduct.get());
            reviewEntity.setImg(review.getImg().getBytes());

            return reviewDao.save(reviewEntity).getDto();
        }
        return null;
    }

}
