package org.icet.learn.service.customer.review;

import lombok.RequiredArgsConstructor;
import org.icet.learn.dto.OrderedProductsResponse;
import org.icet.learn.dto.Product;
import org.icet.learn.entity.CartItemsEntity;
import org.icet.learn.entity.OrderEntity;
import org.icet.learn.repository.OrderDao;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final OrderDao orderDao;

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

}
