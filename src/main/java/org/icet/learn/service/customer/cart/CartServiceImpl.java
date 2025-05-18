package org.icet.learn.service.customer.cart;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.icet.learn.dto.AddProductInCart;
import org.icet.learn.dto.CartItem;
import org.icet.learn.entity.CartItemsEntity;
import org.icet.learn.entity.OrderEntity;
import org.icet.learn.entity.ProductEntity;
import org.icet.learn.entity.UserEntity;
import org.icet.learn.enums.OrderStatus;
import org.icet.learn.repository.CartItemDao;
import org.icet.learn.repository.OrderDao;
import org.icet.learn.repository.ProductDao;
import org.icet.learn.repository.UserDao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{

    private final OrderDao orderDao;

    private final UserDao userDao;

    private final CartItemDao cartItemDao;

    private final ProductDao productDao;

    public ResponseEntity<?> addProductToCart(AddProductInCart addProductInCart) {
        OrderEntity activeOrder = orderDao.findByUserIdAndOrderStatus(
                addProductInCart.getUserId(), OrderStatus.Pending);

        Optional<CartItemsEntity> optionalCartItems = cartItemDao.findByProductIdAndOrderIdAndUserId(
                addProductInCart.getProductId(), activeOrder.getId(), addProductInCart.getUserId());

        if (optionalCartItems.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } else {
            Optional<ProductEntity> optionalProduct = productDao.findById(
                    addProductInCart.getProductId());
            Optional<UserEntity> optionalUser = userDao.findById(
                    addProductInCart.getUserId());

            if (optionalProduct.isPresent() && optionalUser.isPresent()) {
                CartItemsEntity cart = new CartItemsEntity();
                cart.setProduct(optionalProduct.get());
                cart.setPrice(optionalProduct.get().getPrice());
                cart.setQuantity(1L);
                cart.setUser(optionalUser.get());
                cart.setOrder(activeOrder);

                CartItemsEntity updatedCart = cartItemDao.save(cart);

                activeOrder.setTotalAmount(activeOrder.getTotalAmount() + cart.getPrice());
                activeOrder.setAmount(activeOrder.getAmount() + cart.getPrice());
                activeOrder.getCartItems().add(cart);

                orderDao.save(activeOrder);

                CartItem cartDto = mapToDto(updatedCart);
                return ResponseEntity.status(HttpStatus.CREATED).body(cartDto);


            }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or product not found");
        }

    }
    }

    private CartItem mapToDto(CartItemsEntity entity) {
        CartItem dto = new CartItem();
        dto.setId(entity.getId());
        dto.setPrice(entity.getPrice());
        dto.setQuantity(entity.getQuantity());
        dto.setProductId(entity.getProduct().getId());
        dto.setProductName(entity.getProduct().getName());
        return dto;
    }



}
