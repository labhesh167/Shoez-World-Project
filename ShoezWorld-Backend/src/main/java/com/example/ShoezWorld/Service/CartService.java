package com.example.ShoezWorld.Service;

import com.example.ShoezWorld.Model.CartItem;
import com.example.ShoezWorld.Model.Product;
import com.example.ShoezWorld.Model.User;
import com.example.ShoezWorld.Repository.CartItemRepository;
import com.example.ShoezWorld.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<CartItem> getCartItems(User user) {
        return cartItemRepository.findByUser(user);
    }

    /**
     * Adds product to user's cart.
     * If product already exists, increment its quantity.
     */
    public CartItem addOrUpdateCartItem(User user, Product product, int quantity) {
        Optional<CartItem> existingItemOpt = cartItemRepository.findByUserAndProduct(user, product);

        if (existingItemOpt.isPresent()) {
            CartItem existingItem = existingItemOpt.get();
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            return cartItemRepository.save(existingItem);
        } else {
            CartItem newItem = new CartItem();
            newItem.setUser(user);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            return cartItemRepository.save(newItem);
        }
    }

    public void removeCartItem(User user, Long productId) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            Optional<CartItem> itemOpt = cartItemRepository.findByUserAndProduct(user, productOpt.get());
            if (itemOpt.isPresent()) {
                CartItem cartItem = itemOpt.get();
                if (cartItem.getQuantity() > 1) {
                    cartItem.setQuantity(cartItem.getQuantity() - 1);
                    cartItemRepository.save(cartItem); // update quantity
                } else {
                    cartItemRepository.delete(cartItem); // delete item if quantity 1
                }
            }
        }
    }

    public void clearCart(User user) {
        List<CartItem> items = cartItemRepository.findByUser(user);
        cartItemRepository.deleteAll(items);
    }
}
