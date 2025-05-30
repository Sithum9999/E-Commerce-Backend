package org.icet.learn.service.customer.wishlist;

import lombok.RequiredArgsConstructor;
import org.icet.learn.dto.Wishlist;
import org.icet.learn.entity.ProductEntity;
import org.icet.learn.entity.UserEntity;
import org.icet.learn.entity.WishlistEntity;
import org.icet.learn.repository.ProductDao;
import org.icet.learn.repository.UserDao;
import org.icet.learn.repository.WishlistDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final UserDao userDao;
    private final ProductDao productDao;
    private final WishlistDao wishlistDao;

    public Wishlist addProductToWishlist(Wishlist wishlistDto) {
        Optional<ProductEntity> optionalProduct = productDao.findById(wishlistDto.getProductId());
        Optional<UserEntity> optionalUser = userDao.findById(wishlistDto.getUserId());

        if (optionalProduct.isPresent() && optionalUser.isPresent()) {
            WishlistEntity wishlist = new WishlistEntity();
            wishlist.setProduct(optionalProduct.get());
            wishlist.setUser(optionalUser.get());

            return wishlistDao.save(wishlist).getWishlistDto();
        }

        return null;
    }

    public List<Wishlist> getWishlistByUserId(Long userId) {
        return wishlistDao.findAllByUserId(userId).stream()
                .map(WishlistEntity::getWishlistDto)
                .collect(Collectors.toList());
    }

}
