package org.icet.learn.service.customer.cart;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.icet.learn.dto.AddProductInCart;
import org.icet.learn.dto.CartItem;
import org.icet.learn.dto.Order;
import org.icet.learn.dto.PlaceOrder;
import org.icet.learn.entity.*;
import org.icet.learn.enums.OrderStatus;
import org.icet.learn.exceptions.ValidationException;
import org.icet.learn.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{

    private final OrderDao orderDao;

    private final UserDao userDao;

    private final CartItemDao cartItemDao;

    private final ProductDao productDao ;

    private final CouponDao couponDao;

    @Transactional
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

    public Order getCartByUserId(Long userId) {
        OrderEntity activeOrder = orderDao.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
        List<CartItem> cartItemsDtoList = activeOrder.getCartItems().stream().map(CartItemsEntity::getCartDto).collect(Collectors.toList());
        Order orderDto = new Order();
        orderDto.setAmount(activeOrder.getAmount());
        orderDto.setId(activeOrder.getId());
        orderDto.setOrderStatus(activeOrder.getOrderStatus());
        orderDto.setDiscount(activeOrder.getDiscount());
        orderDto.setTotalAmount(activeOrder.getTotalAmount());
        orderDto.setCartItems(cartItemsDtoList);
        if (activeOrder.getCoupon() != null) {
            orderDto.setCouponName(activeOrder.getCoupon().getName());
        }

        return orderDto;
    }

    public Order applyCoupon(Long userId, String code) {
        OrderEntity activeOrder = orderDao.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);

        CouponEntity coupon = couponDao.findByCode(code)
                .orElseThrow(() -> new ValidationException("Coupon not found."));

        if (couponIsExpired(coupon)) {
            throw new ValidationException("Coupon has expired.");
        }

        activeOrder.setCoupon(coupon);
        recalculateAmountWithDiscount(activeOrder);

        orderDao.save(activeOrder);

        return activeOrder.getOrderDto();
    }

    private boolean couponIsExpired(CouponEntity coupon) {
        Date currentDate = new Date();
        Date expirationDate = coupon.getExpirationDate();
        return currentDate.after(expirationDate);
    }

        public Order increaseProductQuantity(AddProductInCart addProductInCart) {
            OrderEntity activeOrder = orderDao.findByUserIdAndOrderStatus(addProductInCart.getUserId(), OrderStatus.Pending);
            Optional<ProductEntity> optionalProduct = productDao.findById(addProductInCart.getProductId());

            Optional<CartItemsEntity> optionalCartItem = cartItemDao.findByProductIdAndOrderIdAndUserId(
                    addProductInCart.getProductId(), activeOrder.getId(), addProductInCart.getUserId()
            );

            if (optionalProduct.isPresent() && optionalCartItem.isPresent()) {
                CartItemsEntity cartItem = optionalCartItem.get();
                ProductEntity product = optionalProduct.get();

                activeOrder.setAmount(activeOrder.getAmount() + product.getPrice());
                activeOrder.setTotalAmount(activeOrder.getTotalAmount() + product.getPrice());

                cartItem.setQuantity(cartItem.getQuantity() + 1);

                if (activeOrder.getCoupon() != null) {
                    recalculateAmountWithDiscount(activeOrder);
                }

                cartItemDao.save(cartItem);
                orderDao.save(activeOrder);
                return activeOrder.getOrderDto();
            }
            return null;
        }

    public Order decreaseProductQuantity(AddProductInCart addProductInCart) {
        OrderEntity activeOrder = orderDao.findByUserIdAndOrderStatus(addProductInCart.getUserId(), OrderStatus.Pending);
        Optional<ProductEntity> optionalProduct = productDao.findById(addProductInCart.getProductId());
        Optional<CartItemsEntity> optionalCartItem = cartItemDao.findByProductIdAndOrderIdAndUserId(
                addProductInCart.getProductId(), activeOrder.getId(), addProductInCart.getUserId()
        );

        if (optionalProduct.isPresent() && optionalCartItem.isPresent()) {
            CartItemsEntity cartItem = optionalCartItem.get();
            ProductEntity product = optionalProduct.get();

            activeOrder.setAmount(activeOrder.getAmount() - product.getPrice());
            activeOrder.setTotalAmount(activeOrder.getTotalAmount() - product.getPrice());

            cartItem.setQuantity(cartItem.getQuantity() - 1);

            if (activeOrder.getCoupon() != null) {
                recalculateAmountWithDiscount(activeOrder);
            }

            cartItemDao.save(cartItem);
            orderDao.save(activeOrder);
            return activeOrder.getOrderDto();
        }

        return null;
    }

    public Order placeOrder(PlaceOrder placeOrder) {
        OrderEntity activeOrder = orderDao.findByUserIdAndOrderStatus(placeOrder.getUserId(), OrderStatus.Pending);
        Optional<UserEntity> optionalUser = userDao.findById(placeOrder.getUserId());
        if (optionalUser.isPresent()) {
            activeOrder.setOrderDescription(placeOrder.getOrderDescription());
            activeOrder.setAddress(placeOrder.getAddress());
            activeOrder.setDate(new Date());
            activeOrder.setOrderStatus(OrderStatus.Placed);
            activeOrder.setTrackingId(UUID.randomUUID());

            orderDao.save(activeOrder);

            OrderEntity order = new OrderEntity();
            order.setAmount(0L);
            order.setTotalAmount(0L);
            order.setDiscount(0L);
            order.setUser(optionalUser.get());
            order.setOrderStatus(OrderStatus.Pending);
            orderDao.save(order);

            return activeOrder.getOrderDto();
        }
        return null;
    }

    private void recalculateAmountWithDiscount(OrderEntity order) {
        if (order.getCoupon() != null) {
            double discountPercentage = order.getCoupon().getDiscount(); // e.g., 50%
            double discountAmount = (discountPercentage / 100.0) * order.getTotalAmount();
            double netAmount = order.getTotalAmount() - discountAmount;

            order.setAmount((long) netAmount);
            order.setDiscount((long) discountAmount);
        } else {
            order.setAmount(order.getTotalAmount());
            order.setDiscount(0L);
        }
    }

    public List<Order> getMyPlacedOrders(Long userId) {
        return orderDao.findByUserIdAndOrderStatusIn(userId, List.of(OrderStatus.Placed, OrderStatus.Shipped, OrderStatus.Delivered))
                .stream()
                .map(OrderEntity::getOrderDto)
                .collect(Collectors.toList());
    }

    public Order searchOrderByTrackingId(UUID trackingId) {
        Optional<OrderEntity> optionalOrder = orderDao.findByTrackingId(trackingId);
        if (optionalOrder.isPresent()){
            return optionalOrder.get().getOrderDto();
        }
        return null;
    }
}