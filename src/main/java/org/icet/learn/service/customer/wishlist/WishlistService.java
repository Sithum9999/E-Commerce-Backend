package org.icet.learn.service.customer.wishlist;

import org.icet.learn.dto.Wishlist;

import java.util.List;

public interface WishlistService {

    Wishlist addProductToWishlist(Wishlist wishlistDto);

    List<Wishlist> getWishlistByUserId(Long userId);

}
